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

    suspend fun GetSummaryDetailsByFilingIdResponse(filing: String): ApiResponse<GetSummaryDetailsByFilingIdResponse>? {
        val resp = doApiCall { api.GetSummaryDetailsByFilingIdResponse(filing).await() }
        return resp
    }
    suspend fun getPaymentOptionByFilingId(filing: String): ApiResponse<PaymentOptionMethodResponse>? {
        val resp = doApiCall { api.getPaymentOptionByFilingId(filing).await() }
        return resp
    }

    suspend fun savePaymentOption(filing: String,i:SavePaymentOptionRequest): ApiResponse<SavePaymentOptionResponse>? {
        val resp = doApiCall { api.savePaymentOption(filing,i).await() }
        return resp
    }

    suspend fun validateXML(filingId: String): ApiResponse<ValidateXMLResponse>? {
        val resp = doApiCall { api.validateXML(filingId).await() }
        return resp
    }
    suspend fun updateRejErrorDesc(filingId: String): ApiResponse<Int>? {
        val resp = doApiCall { api.updateRejErrorDesc(filingId).await() }
        return resp
    }

    suspend fun submissionFee(filingId: String): ApiResponse<SubmissionFeeResponse>? {
        val resp = doApiCall { api.submissionFee(filingId).await() }
        return resp
    }

    suspend fun applyCoupon(filingId: String,i:ApplyCouponRequest): ApiResponse<SubmissionFeeResponse>? {
        val resp = doApiCall { api.applyCoupon(filingId,i).await() }
        return resp
    }

    suspend fun updateConsentDisclosure(filingId: String,i:UpdateConsentDisclosureRequest): ApiResponse<UpdateConsentDisclosureResponse>? {
        val resp = doApiCall { api.updateConsentDisclosure(filingId,i).await() }
        return resp
    }

    suspend fun saveConsentSubmit(filingId: String,i:SaveConsentSubmit): ApiResponse<SaveConsentSubmitResponse>? {
        val resp = doApiCall { api.saveConsentSubmit(filingId,i).await() }
        return resp
    }

    suspend fun getPaymentDet(filingId: String): ApiResponse<GetPaymentDetResponse>? {
        val resp = doApiCall { api.getPaymentDet(filingId).await() }
        return resp
    }
    suspend fun captureCCPayment(filingId: String,i:CaptureCCPaymentRequest): ApiResponse<CaptureCCPaymentResponse>? {
        val resp = doApiCall { api.captureCCPayment(filingId,i).await() }
        return resp
    }

    suspend fun getFormType(): ApiResponse<List<GetFillingFormResponse>>? {
        val resp = doApiCall { api.getFormType().await() }
        return resp
    }

    suspend fun gettaxyear(formtype: String): ApiResponse<List<GetFillingTaxYearResponse>>? {
        val resp = doApiCall { api.gettaxyear(formtype).await() }
        return resp
    }

    suspend fun getFirstUsedMonth(taxYear: String,formtype: String): ApiResponse<List<getFillingFirstUsedMonthResponse>>? {
        val resp = doApiCall { api.getFirstUsedMonth(taxYear,formtype).await() }
        return resp
    }

    suspend fun getTaxableVehicleByFilingId(filing: String): ApiResponse<List<GetTaxableVehicleResponse>>? {
        val resp = doApiCall { api.getTaxableVehicleByFilingId(filing).await() }
        return resp
    }
    suspend fun loadFleetList(filing: String): ApiResponse<List<LoadFleetListResponse>>? {
        val resp = doApiCall { api.loadFleetList(filing).await() }
        return resp
    }
    suspend fun getTGWIncreaseByFilingId(filing: String): ApiResponse<List<GetTGWIncreaseByFilingIdResponse>>? {
        val resp = doApiCall { api.getTGWIncreaseByFilingId(filing).await() }
        return resp
    }

    suspend fun getExceededMileageByFilingId(filing: String): ApiResponse<List<GetExceededMileageByFilingIdResponse>>? {
        val resp = doApiCall { api.getExceededMileageByFilingId(filing).await() }
        return resp
    }

    suspend fun getVinCorrectionByFilingId(filing: String): ApiResponse<List<GetVinCorrectionByFilingIdResponse>>? {
        val resp = doApiCall { api.getVinCorrectionByFilingId(filing).await() }
        return resp
    }

    suspend fun gettaxableweight(): ApiResponse<List<TaxableWeightResponse>>? {
        val resp = doApiCall { api.gettaxableweight().await() }
        return resp
    }
    suspend fun getTGWIncreaseById(id: String, filing: String): ApiResponse<GetTGWIncreaseByIdResponse>? {
        val resp = doApiCall { api.getTGWIncreaseById(id,filing).await() }
        return resp
    }
    suspend fun getExceededMileageById(id: String, filing: String): ApiResponse<GetExceededMileageByIdResponse>? {
        val resp = doApiCall { api.getExceededMileageById(id,filing).await() }
        return resp
    }
    suspend fun getVinCorrectionById(id: String, filing: String): ApiResponse<GetVinCorrectionByIdResponse>? {
        val resp = doApiCall { api.getVinCorrectionById(id,filing).await() }
        return resp
    }
    suspend fun saveTaxableVehicle(filling:String,i: SaveTaxableVehicleRequest?): ApiResponse<SaveTaxableVehicleResponse>? {
        val resp = doApiCall { api.saveTaxableVehicle(filling,i).await() }
        return resp
    }
    suspend fun saveBulkTaxableVehicle(filling:String,i: List<SaveBulkTaxableVehicleRequest>?): ApiResponse<saveBulkTaxableVehicleResponse>? {
        val resp = doApiCall { api.saveBulkTaxableVehicle(filling,i).await() }
        return resp
    }
   suspend fun saveBulkCurrentSuspended(filling:String,i: List<SaveBulkCurrentSuspendedRequest>?): ApiResponse<saveBulkReportingSuspendedResponse>? {
        val resp = doApiCall { api.saveBulkCurrentSuspended(filling,i).await() }
        return resp
    }

    suspend fun saveTGWIncrease(filling:String,i: SaveUpdateTGWIncreaseRequest?): ApiResponse<SaveUpdateTGWIncreaseResponse>? {
        val resp = doApiCall { api.saveTGWIncrease(filling,i).await() }
        return resp
    }
    suspend fun saveExceededMileage(filling:String,i: SaveUpdateExceededMileageRequest?): ApiResponse<SaveUpdateExceededMileageResponse>? {
        val resp = doApiCall { api.saveExceededMileage(filling,i).await() }
        return resp
    }
    suspend fun saveVinCorrection(filling:String,i: SaveUpdateVinCorrectionRequest?): ApiResponse<SaveUpdateVinCorrectionResponse>? {
        val resp = doApiCall { api.saveVinCorrection(filling,i).await() }
        return resp
    }
    suspend fun updateExceededMileage(id:String,filling:String,i: SaveUpdateExceededMileageRequest?): ApiResponse<SaveUpdateExceededMileageResponse>? {
        val resp = doApiCall { api.updateExceededMileage(id,filling,i).await() }
        return resp
    }
    suspend fun updateVinCorrection(id:String,filling:String,i: SaveUpdateVinCorrectionRequest?): ApiResponse<SaveUpdateVinCorrectionResponse>? {
        val resp = doApiCall { api.updateVinCorrection(id,filling,i).await() }
        return resp
    }
    suspend fun updateTGWIncrease(id: String,filling:String,i: SaveUpdateTGWIncreaseRequest?): ApiResponse<SaveUpdateTGWIncreaseResponse>? {
        val resp = doApiCall { api.updateTGWIncrease(id,filling,i).await() }
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
    suspend fun deleteTGWIncreaseById(businessId:String,filling:String): ApiResponse<DeleteTGWIncreaseByIdResponse>? {
        val resp = doApiCall { api.deleteTGWIncreaseById(businessId,filling).await() }
        return resp
    }
    suspend fun deleteExceededMileageById(businessId:String,filling:String): ApiResponse<DeleteExceededMileageByIdResponse>? {
        val resp = doApiCall { api.deleteExceededMileageById(businessId,filling).await() }
        return resp
    }

    suspend fun deleteVinCorrectionById(businessId:String,filling:String): ApiResponse<DeleteVinCorrectionByIdResponse>? {
        val resp = doApiCall { api.deleteVinCorrectionById(businessId,filling).await() }
        return resp
    }
    suspend fun deleteCurrentSuspendedeById(businessId:String,filling:String): ApiResponse<DeleteCurrentSuspendedeById>? {
        val resp = doApiCall { api.deleteCurrentSuspendedeById(businessId,filling).await() }
        return resp
    }
    suspend fun deleteSoldDestroyedById(businessId:String,filling:String): ApiResponse<DeleteSoldDestroyedResponse>? {
        val resp = doApiCall { api.deleteSoldDestroyedById(businessId,filling).await() }
        return resp
    }
    suspend fun deleteCreditOverPaymentById(businessId:String,filling:String): ApiResponse<DeleteCreditOverPaymentById>? {
        val resp = doApiCall { api.deleteCreditOverPaymentById(businessId,filling).await() }
        return resp
    }
    suspend fun deleteLowMileageById(businessId:String,filling:String): ApiResponse<DeleteLowMileageByIdResponse>? {
        val resp = doApiCall { api.deleteLowMileageById(businessId,filling).await() }
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
    suspend fun editgetSoldDestroyedById(id: String,filling:String): ApiResponse<EditgetSoldDestroyedByIdResponse>? {
        val resp = doApiCall { api.editgetSoldDestroyedById(id,filling).await() }
        return resp
    }
    suspend fun getCreditOverPaymentById(id: String,filling:String): ApiResponse<GetCreditOverPaymentByIdResponse>? {
        val resp = doApiCall { api.getCreditOverPaymentById(id,filling).await() }
        return resp
    }
    suspend fun getLowMileageById(id: String,filling:String): ApiResponse<GetLowMileageByIdResponse>? {
        val resp = doApiCall { api.getLowMileageById(id,filling).await() }
        return resp
    }

    suspend fun getPriorSuspendedByFilingId(filling:String): ApiResponse<List<GetPriorSuspendedByFilingIdResponse>>? {
        val resp = doApiCall { api.getPriorSuspendedByFilingId(filling).await() }
        return resp
    }
    suspend fun getPriorSoldDate(filling:String): ApiResponse<GetPriorSoldDateResponse>? {
        val resp = doApiCall { api.getPriorSoldDate(filling).await() }
        return resp
    }
    suspend fun getSoldAndDestoryDate(filling:String): ApiResponse<GetSoldAndDestoryDateResponse>? {
        val resp = doApiCall { api.getSoldAndDestoryDate(filling).await() }
        return resp
    }
    suspend fun getLowMileageDate(filling:String): ApiResponse<GetLowMileageDateResponse>? {
        val resp = doApiCall { api.getLowMileageDate(filling).await() }
        return resp
    }
    suspend fun getCreditOverPaymentDate(filling:String): ApiResponse<GetCreditOverPaymentDateResponse>? {
        val resp = doApiCall { api.getCreditOverPaymentDate(filling).await() }
        return resp
    }

    suspend fun savePriorSuspended(filling:String, i : SavePriorSuspendedRequest): ApiResponse<SavePriorSuspendedResponse>? {
        val resp = doApiCall { api.savePriorSuspended(filling,i).await() }
        return resp
    }

    suspend fun deletePriorSuspendedById(id:String,filling:String): ApiResponse<DeletePriorSuspendedResponse>? {
        val resp = doApiCall { api.deletePriorSuspendedById(id,filling).await() }
        return resp
    }

    suspend fun getPriorSuspendedById(id:String,filling:String): ApiResponse<GetPriorSuspendedByIdResponse>? {
        val resp = doApiCall { api.getPriorSuspendedById(id,filling).await() }
        return resp
    }

    suspend fun saveSoldDestroyedVehicle(filling:String,i:SaveSoldDestroyedVehicleRequest): ApiResponse<SaveSoldDestroyedVehicleResponse>? {
        val resp = doApiCall { api.saveSoldDestroyedVehicle(filling,i).await() }
        return resp
    }
    suspend fun saveCreditOverPayment(filling:String,i:SaveCreditOverPaymentRequest): ApiResponse<SaveCreditOverPaymentResponse>? {
        val resp = doApiCall { api.saveCreditOverPayment(filling,i).await() }
        return resp
    }
    suspend fun updateCreditOverPayment(id:String,filling:String,i:UpdateCreditOverPaymentRequest): ApiResponse<UpdateCreditOverPaymentResponse>? {
        val resp = doApiCall { api.updateCreditOverPayment(id,filling,i).await() }
        return resp
    }
    suspend fun saveLowMileageVehicle(filling:String,i:SaveLowMileageVehicleRequest): ApiResponse<SaveLowMileageVehicleResponse>? {
        val resp = doApiCall { api.saveLowMileageVehicle(filling,i).await() }
        return resp
    }
    suspend fun updateLowMileageVehicle(id:String,filling:String,i:UpdateLowMileageVehicleRequest): ApiResponse<UpdateLowMileageVehicleResponse>? {
        val resp = doApiCall { api.updateLowMileageVehicle(id,filling,i).await() }
        return resp
    }
    suspend fun updateSoldDestroyedVehicle(id:String,filling:String,i:UpdateSoldDestroyedVehicleRequest): ApiResponse<updateSoldDestroyedVehicle>? {
        val resp = doApiCall { api.updateSoldDestroyedVehicle(id,filling,i).await() }
        return resp
    }

    suspend fun getSoldDestroyedByFilingId(filling:String): ApiResponse<List<GetSoldDestroyedByFilingIdResponse>>? {
        val resp = doApiCall { api.getSoldDestroyedByFilingId(filling).await() }
        return resp
    }

    suspend fun getCreditOverPaymentByFilingId(filling:String): ApiResponse<List<GetCreditOverPaymentByFilingIdResponse>>? {
        val resp = doApiCall { api.getCreditOverPaymentByFilingId(filling).await() }
        return resp
    }


    suspend fun getLowMileageByFilingId(filling:String): ApiResponse<List<GetLowMileageByFilingIdResponse>>? {
        val resp = doApiCall { api.getLowMileageByFilingId(filling).await() }
        return resp
    }

    suspend fun saveFiling(i: SaveUpdateFilingRequest?): ApiResponse<SaveUpdateFilingResponse>? {
        val resp = doApiCall { api.saveFiling(i).await() }
        return resp
    }

    suspend fun updateFiling(filingId: String,i: SaveUpdateFilingRequest?): ApiResponse<SaveUpdateFilingResponse>? {
        val resp = doApiCall { api.updateFiling(filingId,i).await() }
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

    suspend fun getFilingById(filingId: String?): ApiResponse<GetFilingByIdResponse>? {
        val resp = doApiCall { api.getFilingById(filingId).await() }
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