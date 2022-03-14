package com.cctv.xh.ui

import android.content.Intent
import com.cctv.libbbbbb.BaseAct
import com.cctv.xh.R
import com.roger.catloadinglibrary.CatLoadingView

class Splash : BaseAct(R.layout.activity_splash) {

    private val cat by lazy {
        CatLoadingView()
    }

    override fun oooo() {

    }

    override fun ooooo() {
        startActivity(Intent(this, MapsActivity::class.java))
        finish()
    }

    override fun showCat() {
        cat.show(supportFragmentManager,"")
    }

    override fun closeCat() {
       cat.dismiss()
    }
}