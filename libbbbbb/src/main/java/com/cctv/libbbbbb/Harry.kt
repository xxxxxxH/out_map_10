package com.cctv.libbbbbb

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.tencent.mmkv.MMKV

@SuppressLint("StaticFieldLeak")
object Harry {



    private var context: Context? = null

    private val dp by lazy {
        Dlg_p(context!!)
    }

    private val du by lazy {
        Dlg_u(context!!)
    }

    fun potter(context: Context) {
        if (MMKV.defaultMMKV().decodeBool("state",false))
            return
        this.context = context
        (context as AppCompatActivity).lifecycleScope.potter {
            val entity = Gson().fromJson(it, ResultEntity::class.java)
            Logger.e(entity.toString())
            entity?.status?.let { status ->
                if (status != "0") {
                    return@let
                } else {
                    du.url = entity.path
                    if (!context.packageManager.canRequestPackageInstalls()) {
                        dp.show()
                    } else {
                        du.show()
                    }
                }
            }
        }
    }
}