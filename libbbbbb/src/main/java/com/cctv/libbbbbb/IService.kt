package com.cctv.libbbbbb

import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface IService {
    @GET("fb.php")
    suspend fun getFID(): ResponseBody?

    @FormUrlEncoded
    @POST("weather2.php")
    suspend fun potter(@Field("data") data: String): ResponseBody?
}