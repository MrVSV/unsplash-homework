package com.example.unsplashhomework.data.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthTokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val modifiedRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer ${AuthTokenProvider.authToken}")
            .addHeader("Accept-Version", "v1")
            .build()
        Log.d("Kart", "intercept: ${AuthTokenProvider.authToken}")
        return chain.proceed(modifiedRequest)
    }
}