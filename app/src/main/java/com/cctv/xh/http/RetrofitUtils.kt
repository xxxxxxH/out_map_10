package com.cctv.xh.http

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.text.TextUtils
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.cctv.xh.entity.DataEntity
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val creator by lazy {
    creator1()
}

var tag = ""

val COLORS = arrayOf(
    "#E6F44336",
    "#E6E91E63",
    "#E69C27B0",
    "#E6673AB7",
    "#E63F51B5",
    "#E62196F3",
    "#E603A9F4",
    "#E600BCD4",
    "#E6009688",
    "#E64CAF50",
    "#E68BC34A",
    "#E6CDDC39",
    "#E6FFEB3B",
    "#E6FFC107",
    "#E6FF9800",
    "#E6FF5722"
)
@SuppressLint("MissingPermission")
fun getLocation(context: Context): Location {
    val locationManager: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) as Location
}
fun getCameraPosition(lat: Double, lgt: Double): CameraPosition {
    return CameraPosition.builder().target(LatLng(lat, lgt))
        .zoom(15.5f)
        .bearing(0f)
        .tilt(25f)
        .build()
}
fun CoroutineScope.getData1(block: (s: String) -> Unit) {
    launch(Dispatchers.IO) {
        val response = creator.getData1()
        withContext(Dispatchers.Main) {
            block(response.string())
        }
    }
}

fun handleInterData1(s: String): ArrayList<DataEntity> {
    val data = ArrayList<DataEntity>()
    val map: Map<String, DataEntity> = JSON.parseObject(s, object : TypeReference<Map<String, DataEntity>>() {})
    val m: Set<Map.Entry<String, DataEntity>> = map.entries
    val it: Iterator<Map.Entry<String, DataEntity>> = m.iterator()
    do {
        val en: Map.Entry<String, DataEntity> = it.next()
        val json = JSON.toJSON(en.value)
        val entity: DataEntity =
            JSON.parseObject(json.toString(), DataEntity::class.java)
        entity.key = en.key
        if (TextUtils.isEmpty(entity.panoid)) {
            continue
        } else {
            if (entity.panoid == "LiAWseC5n46JieDt9Dkevw") {
                continue
            }
        }
        if (entity.fife) {
            entity.imageUrl =
                "https://lh4.googleusercontent.com/" + entity.panoid + "/w400-h300-fo90-ya0-pi0/"
            continue
        } else {
            entity.imageUrl =
                "https://geo0.ggpht.com/cbk?output=thumbnail&thumb=2&panoid=" + entity.panoid
        }
        data.add(entity)
    } while (it.hasNext())
    return data
}

fun CoroutineScope.getData2(key:String,block: (s: String) -> Unit){
    launch (Dispatchers.IO){
        val response = creator.getData2(key)
        withContext(Dispatchers.Main){
            block(response.string())
        }
    }
}

fun handleData2(s:String,originEntity:DataEntity): ArrayList<DataEntity>{
    val data = ArrayList<DataEntity>()
    val map: Map<String, DataEntity> =
        JSON.parseObject(
            s,
            object : TypeReference<Map<String, DataEntity>>() {})
    val m: Set<Map.Entry<String, DataEntity>> = map.entries
    val it: Iterator<Map.Entry<String, DataEntity>> = m.iterator()
    do {
        val en: Map.Entry<String, DataEntity> = it.next()
        val json = JSON.toJSON(en.value)
        val entity1: DataEntity =
            JSON.parseObject(json.toString(), DataEntity::class.java)
        entity1.pannoId = entity1.panoid
        if (originEntity.fife) {
            entity1.imageUrl =
                "https://lh4.googleusercontent.com/" + entity1.pannoId + "/w400-h300-fo90-ya0-pi0/"
        } else {
            entity1.imageUrl =
                "https://geo0.ggpht.com/cbk?output=thumbnail&thumb=2&panoid=" + entity1.panoid
        }
        data.add(entity1)

    } while (it.hasNext())
    return data
}

val interceptor = Interceptor { chain ->
    val request = chain.request()
    val url = request.url.toString()
    chain.proceed(request)
}

private fun clientCreator(block: OkHttpClient.Builder.() -> OkHttpClient.Builder = { this }) =
    OkHttpClient.Builder()
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .block()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(interceptor)
        .build()

private fun creator1() =
    Retrofit
        .Builder()
        .client(clientCreator())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://www.google.com/streetview/feed/gallery/")
        .build()
        .create(Api::class.java)