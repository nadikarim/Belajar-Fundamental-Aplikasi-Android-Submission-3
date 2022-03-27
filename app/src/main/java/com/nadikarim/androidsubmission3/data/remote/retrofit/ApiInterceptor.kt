package com.nadikarim.androidsubmission3.data.remote.retrofit

import com.nadikarim.androidsubmission3.util.Constants.TOKEN
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "token $TOKEN")
            .build()
        return chain.proceed(request)
    }
}