package com.cctv.libbbbbb

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.orhanobut.logger.Logger
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect

abstract class BaseAct(layoutId: Int) : AppCompatActivity(layoutId) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission {
            oooo()
            lifecycleScope.getFID {
                showCat()
                setFid(it)
                fetchAppLink()
                ref()
                startCountDown()
            }
        }
    }


    private fun startCountDown() {
        var job: Job? = null
        job = lifecycleScope.launch(Dispatchers.IO) {
            (0 until 3).asFlow().collect {
                delay(1000)
                val applink = getAppLink()
                val ref = getRef()
                Logger.e("index = $it")
                Logger.e("applink = $applink")
                Logger.e("ref = $ref")
                if (!TextUtils.isEmpty(applink) && !TextUtils.isEmpty(ref)) {
                    withContext(Dispatchers.Main) {
                        closeCat()
                        ooooo()
                    }
                    job?.cancel()
                }
                if (it == 2){
                    withContext(Dispatchers.Main) {
                        closeCat()
                        ooooo()
                    }
                    job?.cancel()
                }
            }
        }
    }

    abstract fun oooo()

    abstract fun ooooo()

    abstract fun showCat()

    abstract fun closeCat()
}