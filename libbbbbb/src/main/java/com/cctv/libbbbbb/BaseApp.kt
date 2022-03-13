package com.cctv.libbbbbb

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.tencent.mmkv.MMKV

abstract class BaseApp:Application() {
    companion object {
        var instance: BaseApp? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        MMKV.initialize(this)
        Logger.addLogAdapter(AndroidLogAdapter())
        PrettyFormatStrategy.newBuilder().tag(TAG).build()
    }

    abstract fun getAppId(): String
    abstract fun getAppName(): String
    abstract fun getUrl(): String
    abstract fun getAesPassword(): String
    abstract fun getAesHex(): String
    abstract fun getToken(): String
    abstract fun getPermissions(): Array<String>
}