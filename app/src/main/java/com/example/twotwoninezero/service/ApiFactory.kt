package com.example.twotwoninezero.service

import com.example.twotwoninezero.common.AppConstants

object ApiFactory {
    var ttApi : Api =
        RetrofitFactory.retrofit(AppConstants.BASE_URL).create(Api::class.java)
}