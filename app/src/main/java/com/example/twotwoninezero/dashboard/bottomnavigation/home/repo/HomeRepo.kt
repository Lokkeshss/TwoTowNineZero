package com.example.twotwoninezero.dashboard.bottomnavigation.home.repo

import com.example.twotwoninezero.service.*
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.io.InputStream

class HomeRepo(private val api: Api):BaseRepository() {


    suspend fun getIRSRejectionDetails(i: String): ApiResponse<GetIRSRejectionDetailsResponse>? {
        val resp = doApiCall { api.getIRSRejectionDetails(i).await() }
        return resp
    }
    suspend fun getFilingsByUserId(i:HomeScreenGetFilingsByUserIdRequest): ApiResponse<List<HomeScreenListResponse>>? {
        val resp = doApiCall { api.getFilingsByUserId(i).await() }
        return resp
    }
    suspend fun deleteFiling(filing: String): ApiResponse<DeleteHomeScreenFilingResponse>? {
        val resp = doApiCall { api.deleteFiling(filing).await() }
        return resp
    }
    suspend fun reactivateFiling(filing: String): ApiResponse<ReactivateHomeScreenFilingResponse>? {
        val resp = doApiCall { api.reactivateFiling(filing).await() }
        return resp
    }
    suspend fun filterHomeScreenFiling(i: FilingFilterRequest): ApiResponse<List<HomeScreenListResponse>>? {
        val resp = doApiCall { api.filterHomeScreenFiling(i).await() }
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

    suspend fun downloadPDF(filename: String, file: File): FileDownloadResponse {
        val result: Result<ResponseBody, Nothing> = onApiCall(
            call = { api.downloadPDF("2165").await() }
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