package com.example.twotwoninezero.dashboard.bottomnavigation.filling.repo

import com.example.twotwoninezero.service.*
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.io.InputStream


class FillingRepo(private val api: Api) : BaseRepository()  {


   /* suspend fun getallbusinesslist(): ApiResponse<List<AllAndSearchBusinessListResponse>>? {
        val resp = doApiCall { api.getallbusinesslist().await() }
        return resp
    }*/

    suspend fun getFormType(): ApiResponse<List<GetFillingFormResponse>>? {
        val resp = doApiCall { api.getFormType().await() }
        return resp
    }

    suspend fun gettaxyear(formtype: String): ApiResponse<List<GetFillingTaxYearResponse>>? {
        val resp = doApiCall { api.gettaxyear(formtype).await() }
        return resp
    }

    suspend fun getFirstUsedMonth(formtype: String): ApiResponse<List<getFillingFirstUsedMonthResponse>>? {
        val resp = doApiCall { api.getFirstUsedMonth(formtype).await() }
        return resp
    }

    suspend fun getTaxableVehicleByFilingId(filing: String): ApiResponse<List<GetTaxableVehicleResponse>>? {
        val resp = doApiCall { api.getTaxableVehicleByFilingId(filing).await() }
        return resp
    }

    suspend fun gettaxableweight(): ApiResponse<List<TaxableWeightResponse>>? {
        val resp = doApiCall { api.gettaxableweight().await() }
        return resp
    }
    suspend fun saveTaxableVehicle(filling:String,i: SaveTaxableVehicleRequest?): ApiResponse<SaveTaxableVehicleResponse>? {
        val resp = doApiCall { api.saveTaxableVehicle(filling,i).await() }
        return resp
    }
    suspend fun updateTaxableVehicle(businessId:String,filling:String,i: SaveTaxableVehicleRequest?): ApiResponse<SaveTaxableVehicleResponse>? {
        val resp = doApiCall { api.updateTaxableVehicle(businessId,filling,i).await() }
        return resp
    }
    suspend fun deleteTaxableVehicleById(businessId:String,filling:String): ApiResponse<DeleteTaxableVehicleResponse>? {
        val resp = doApiCall { api.deleteTaxableVehicleById(businessId,filling).await() }
        return resp
    }
    suspend fun deleteCurrentSuspendedeById(businessId:String,filling:String): ApiResponse<DeleteCurrentSuspendedeById>? {
        val resp = doApiCall { api.deleteCurrentSuspendedeById(businessId,filling).await() }
        return resp
    }
    suspend fun GetCurrentSuspendedByFilingId(filling:String): ApiResponse<List<GetCurrentSuspendedByFilingIdResponse>>? {
        val resp = doApiCall { api.GetCurrentSuspendedByFilingId(filling).await() }
        return resp
    }
    suspend fun saveCurrentSuspended(filling:String,i:SaveCurrentSuspendRequest): ApiResponse<SaveCurrentSuspendResponse>? {
        val resp = doApiCall { api.saveCurrentSuspended(filling,i).await() }
        return resp
    }
    suspend fun updateCurrentSuspended(id: String,filling:String,i:UpdateCurrentSuspendRequest): ApiResponse<UpdateCurrentSuspendResponse>? {
        val resp = doApiCall { api.updateCurrentSuspended(id,filling,i).await() }
        return resp
    }
    suspend fun updatePriorSuspended(id: String,filling:String,i:UpdatePriorSuspendedRequest): ApiResponse<UpdatePriorSuspendedResponse>? {
        val resp = doApiCall { api.updatePriorSuspended(id,filling,i).await() }
        return resp
    }
    suspend fun editGetCurrentSuspendedById(id: String,filling:String): ApiResponse<EditGetCurrentSuspendedByIdResponse>? {
        val resp = doApiCall { api.editGetCurrentSuspendedById(id,filling).await() }
        return resp
    }

    suspend fun getPriorSuspendedByFilingId(filling:String): ApiResponse<List<GetPriorSuspendedByFilingIdResponse>>? {
        val resp = doApiCall { api.getPriorSuspendedByFilingId(filling).await() }
        return resp
    }

    suspend fun savePriorSuspended(filling:String, i : SavePriorSuspendedRequest): ApiResponse<SavePriorSuspendedResponse>? {
        val resp = doApiCall { api.savePriorSuspended(filling,i).await() }
        return resp
    }

    suspend fun saveFiling(i: SaveUpdateFilingRequest?): ApiResponse<SaveUpdateFilingResponse>? {
        val resp = doApiCall { api.saveFiling(i).await() }
        return resp
    }



    suspend fun getCountry(): ApiResponse<List<GetCountryItem>>? {
        val resp = doApiCall { api.getCountry().await() }
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

    suspend fun getTaxableVehicleById(businessId:String,filing: String): ApiResponse<EditTaxableVehicleByIdResponse>? {
        val resp = doApiCall { api.getTaxableVehicleById(businessId,filing).await() }
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