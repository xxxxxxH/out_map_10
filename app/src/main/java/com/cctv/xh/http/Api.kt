package com.cctv.xh.http

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("data.json")
    suspend fun getData1(): ResponseBody

    @GET("collection/{key}.json")
    suspend fun getData2(@Path("key") key: String): ResponseBody
}