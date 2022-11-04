package com.example.twotwoninezero.service

import com.example.twotwoninezero.ThisApplication
import com.example.twotwoninezero.common.AppConstants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    private val newAuthInterceptor = Interceptor { chain ->
        synchronized(this) {
            val keepAliveUrl = AppConstants.BASE_URL
            var tkn: String? = ThisApplication.publicPrefs.jwtToken

            val originalRequest = chain.request()

            when {
                originalRequest.header("No-Authentication")==null -> {
                    val authenticationRequest = originalRequest.newBuilder()
                        .addHeader("Authorization", "Bearer ${tkn}")
                        .build()

                    val initialResponse = chain.proceed(authenticationRequest)

                    when {
                        initialResponse.code() == 401 -> {
                            //RUN BLOCKING!!
                            val resp = runBlocking {
                                val jsonString = "{\"tkn\":\"${tkn}\"}"
                                var postBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString)

                                var refreshtokenRequest = chain.request().newBuilder().post(postBody).url(keepAliveUrl).build()
                                chain.proceed(refreshtokenRequest)
                            }

                            when {
                                resp == null || resp.code() != 200 -> {
                                    resp
                                }
                                else -> {
                                    val newresp = resp.body()?.string()
                                    val jsonObj = JSONObject(newresp)
                                    tkn = jsonObj.optString("tkn", null)
                                    ThisApplication.publicPrefs.jwtToken = tkn
                                    val newAuthenticationRequest = originalRequest.newBuilder().addHeader("Authorization",
                                        "Bearer $tkn"
                                    ).build()
                                    chain.proceed(newAuthenticationRequest)
                                }
                            }
                        }
                        else -> initialResponse
                    }
                }
                else -> chain.proceed(originalRequest)
            }
        }
    }


    private val loggingInterceptor =  HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }


    private fun createClientAuth(): OkHttpClient {
        //ADD DISPATCHER WITH MAX REQUEST TO 1
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        okHttpClientBuilder.dispatcher(dispatcher)
        okHttpClientBuilder.addInterceptor(RetrofitFactory.newAuthInterceptor)
        okHttpClientBuilder.addInterceptor(loggingInterceptor)
        okHttpClientBuilder.readTimeout(120, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(120, TimeUnit.SECONDS)
        okHttpClientBuilder.connectTimeout(120, TimeUnit.SECONDS)
        return okHttpClientBuilder.build()
    }


    fun retrofit(baseUrl : String) : Retrofit = Retrofit.Builder()
        .client(createClientAuth())
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

}