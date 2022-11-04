package com.example.twotwoninezero.dashboard.bottomnavigation.business.repo

import com.example.twotwoninezero.service.*
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.io.InputStream


class BusinessRepo(private val api: Api) : BaseRepository()  {

    suspend fun getCountry(): ApiResponse<List<GetCountryItem>>? {
        val resp = doApiCall { api.getCountry().await() }
        return resp
    }

    suspend fun getBusinessTypeRequestItem(): ApiResponse<List<GetBusinessTypeRequestItem>>? {
        val resp = doApiCall { api.getBusinessTypeRequestItem().await() }
        return resp
    }

    suspend fun getState(i:String): ApiResponse<List<GetStateReponse>>? {
        val resp = doApiCall { api.getState(i).await() }
        return resp
    }

    suspend fun getallbusinesslist( listType: String?,limit: String?,offset: String?): ApiResponse<List<AllAndSearchBusinessListResponse>>? {
        val resp = doApiCall { api.getallbusinesslist(listType,limit,offset).await() }
        return resp
    }

    suspend fun searchBusiness(i:SearchBusinessRequest): ApiResponse<List<AllAndSearchBusinessListResponse>>? {
        val resp = doApiCall { api.searchBusiness(i).await() }
        return resp
    }

    suspend fun createNewBusinessRequest(i: CreateAndUpdateBusinessRequest): ApiResponse<UpdateAndNewBusinessResponse>? {
        val resp = doApiCall { api.createNewBusinessRequest(i).await() }
        return resp
    }
    suspend fun updateBusinessRequest(id:String,i: CreateAndUpdateBusinessRequest): ApiResponse<UpdateAndNewBusinessResponse>? {
        val resp = doApiCall { api.updateBusinessRequest(id,i).await() }
        return resp
    }
    suspend fun editBusinessList(businessId: String): ApiResponse<EditBusinessListResponse>? {
        val resp = doApiCall { api.editBusinessList(businessId).await() }
        return resp
    }
    suspend fun archiveBusinesss(businessId: String): ApiResponse<ArchiveBusinessResponse>? {
        val resp = doApiCall { api.archiveBusinesss(businessId).await() }
        return resp
    }
    suspend fun reActiveBusinesss(businessId: String): ApiResponse<ReActivateBusinessResponse>? {
        val resp = doApiCall { api.reActiveBusinesss(businessId).await() }
        return resp
    }



    private suspend fun <T : Any> onApiCall(call: suspend () -> Response<T>): Result<T, Nothing> {

        try {
            val response = call.invoke()

            if (response.isSuccessful) {
                return Result.Success(response.body()!!)
            } else {
                return Result.Error(IOException(response.errorBody().toString()))
            }
        }catch (e : Exception){
            return Result.Error(IOException(e))
        }

    }


    suspend fun doDownloadFile(filename: String, file: File): FileDownloadResponse {
        val result: Result<ResponseBody, Nothing> = onApiCall(
            call = { api.download_file().await() }
        )
        var finalresp =  FileDownloadResponse(false, null,null)
        when (result) {
            is Result.Success -> {
                try {
                    val fileReader = ByteArray(4096)
                    val inputStream: InputStream = result.data.byteStream()
                    val fOut = file.outputStream()
                    while (true) {
                        val read: Int = inputStream.read(fileReader)
                        if (read == -1) {
                            break
                        }
                        fOut.write(fileReader, 0, read)
                    }
                    fOut.flush()
                    fOut.close()
                    finalresp.msg = "Downloaded successfully"
                    finalresp.file = file
                } catch (e: Exception) {
                    finalresp.error = true
                    finalresp.msg = "Download Failed"
                    finalresp.file = null
                    e.printStackTrace()
                }
            }
            is Result.ApiError -> {
                finalresp.error = true
            }
            else -> {}
        }
        return finalresp
    }


}