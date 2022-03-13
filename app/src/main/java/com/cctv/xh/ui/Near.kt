package com.cctv.xh.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cctv.xh.R
import com.cctv.xh.holder.H2
import com.cleveroad.fanlayoutmanager.FanLayoutManager
import com.cleveroad.fanlayoutmanager.FanLayoutManagerSettings
import kotlinx.android.synthetic.main.activity_near.*
import zhan.auto_adapter.AutoRecyclerAdapter


class Near : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_near)
        val data = ArrayList<String>()
        data.add("airport")
        data.add("atm")
        data.add("bakery")
        data.add("bank")
        data.add("bus")
        data.add("cafe")
        data.add("church")
        data.add("cloth")
        data.add("dentist")
        data.add("doctor")
        data.add("fire station")
        data.add("gas station")
        data.add("hospital")
        data.add("hotel")
        data.add("jewelry")
        data.add("mall")
        data.add("mosque")
        data.add("park")
        data.add("pharmacy")
        data.add("police")
        data.add("post office")
        data.add("salon")
        data.add("shoe")
        data.add("stadium")
        data.add("university")
        data.add("zoo")
        val adapter = AutoRecyclerAdapter()
        recycler.adapter = adapter
        val fanLayoutManagerSettings = FanLayoutManagerSettings
            .newBuilder(this)
            .withFanRadius(true)
            .withAngleItemBounce(5f)
            .withViewWidthDp(120f)
            .withViewHeightDp(160f)
            .build()
        recycler.layoutManager = FanLayoutManager(this,fanLayoutManagerSettings)
        adapter.setHolder(H2::class.java, R.layout.layout_item2)
        adapter.setDataList(H2::class.java, data).notifyDataSetChanged()
    }
}