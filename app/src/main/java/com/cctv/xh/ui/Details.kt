package com.cctv.xh.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cctv.xh.R
import com.cctv.xh.entity.DataEntity
import com.cctv.xh.holder.Holder_1
import com.cctv.xh.http.getData2
import com.cctv.xh.http.handleData2
import com.cctv.xh.http.tag
import com.roger.catloadinglibrary.CatLoadingView
import kotlinx.android.synthetic.main.activity_details.*
import zhan.auto_adapter.AutoRecyclerAdapter

class Details : AppCompatActivity() {

    private val entity by lazy {
        intent.getSerializableExtra("data") as DataEntity
    }

    private val cat by lazy {
        CatLoadingView()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        tag = "2"
        cat.show(supportFragmentManager, "")
        cat.setClickCancelAble(false)
        lifecycleScope.getData2(entity.key) {
            val data = handleData2(it, entity)
            val adapter = AutoRecyclerAdapter()
            recycler.layoutManager = LinearLayoutManager(this@Details)
            recycler.adapter = adapter
            adapter.setHolder(Holder_1::class.java, R.layout.item_inter)
            adapter.setDataList(Holder_1::class.java, data).notifyDataSetChanged()
            cat.dismiss()
        }
    }
}