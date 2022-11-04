package com.example.twotwoninezero.dashboard.bottomnavigation.fleet.repo

import com.example.twotwoninezero.service.*

class Fleetrepo(private val api: Api) : BaseRepository()   {

    suspend fun getbusinessname(): ApiResponse<List<GetBusinessNameResponse>>? {
        val resp = doApiCall { api.getbusinessname().await() }
        return resp
    }

    suspend fun gettaxableweight(): ApiResponse<List<TaxableWeightResponse>>? {
        val resp = doApiCall { api.gettaxableweight().await() }
        return resp
    }
    suspend fun addNewFleetBusinessRequest(i: AddNewFleetBusinessRequest): ApiResponse<AddNewFleetResponse>? {
        val resp = doApiCall { api.addNewFleetBusinessRequest(i).await() }
        return resp
    }
    suspend fun updateFleetlineItem(fleetId:String,i: AddNewFleetBusinessRequest): ApiResponse<AddNewFleetResponse>? {
        val resp = doApiCall { api.updateFleetlineItem(fleetId,i).await() }
        return resp
    }
    suspend fun getFleetById(i: String): ApiResponse<GetFleetByIdResponse>? {
        val resp = doApiCall { api.getFleetById(i).await() }
        return resp
    }

    suspend fun getfleetlist(i: String): ApiResponse<List<GetFleetListResponse>>? {
        val resp = doApiCall { api.getfleetlist(i).await() }
        return resp
    }
    suspend fun deleteFleetlineItem(i: String): ApiResponse<DeleteFleetResponse>? {
        val resp = doApiCall { api.deleteFleetlineItem(i).await() }
        return resp
    }

}