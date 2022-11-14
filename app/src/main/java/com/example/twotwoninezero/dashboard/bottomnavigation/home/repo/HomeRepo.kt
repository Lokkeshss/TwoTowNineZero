package com.example.twotwoninezero.dashboard.bottomnavigation.home.repo

import com.example.twotwoninezero.service.*

class HomeRepo(private val api: Api):BaseRepository() {


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

}