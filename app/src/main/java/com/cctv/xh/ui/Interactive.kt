package com.cctv.xh.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cctv.xh.R
import com.cctv.xh.holder.Holder_1
import com.cctv.xh.http.getData1
import com.cctv.xh.http.handleInterData1
import com.cctv.xh.http.tag
import com.roger.catloadinglibrary.CatLoadingView
import kotlinx.android.synthetic.main.activity_interactive.*
import zhan.auto_adapter.AutoRecyclerAdapter

class Interactive : AppCompatActivity() {
    private val cat by lazy {
        CatLoadingView()
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interactive)
        tag = "1"
        cat.show(supportFragmentManager,"")
        cat.setClickCancelAble(false)
        lifecycleScope.getData1 {
            val data = handleInterData1(it)
            val adapter = AutoRecyclerAdapter()
            recycler.layoutManager = LinearLayoutManager(this@Interactive)
            recycler.adapter = adapter
            adapter.setHolder(Holder_1::class.java, R.layout.item_inter)
            adapter.setDataList(Holder_1::class.java, data).notifyDataSetChanged()
            cat.dismiss()
        }
    }
}