package com.cctv.libbbbbb

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class IInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response: Response = chain.proceed(request)
        return if (response.code == 200) {
            response
        } else {
            response.newBuilder().body("{}".toResponseBody("text/plain".toMediaTypeOrNull())).build()
        }
    }
}