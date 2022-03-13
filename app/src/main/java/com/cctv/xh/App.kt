package com.cctv.xh

import android.Manifest
import com.cctv.libbbbbb.BaseApp
import com.tencent.mmkv.MMKV
import java.util.*

class App:BaseApp() {
    override fun getAppId(): String {
        return "461"
    }

    override fun getAppName(): String {
        return "net.basicmodel"
    }

    override fun getUrl(): String {
        return "https://pddsale.xyz/livemap461/"
    }

    override fun getAesPassword(): String {
        return "rFtVnzKzqCPoprxt"
    }

    override fun getAesHex(): String {
        return "rFtVnzKzqCPoprxt"
    }

    override fun getToken(): String {
        var token = ""
        token = if (MMKV.defaultMMKV()!!.decodeString("token","") == ""){
            UUID.randomUUID().toString()
        }else{
            MMKV.defaultMMKV()!!.decodeString("token","")!!
        }
        return token
    }

    override fun getPermissions(): Array<String> {
        return arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }
}