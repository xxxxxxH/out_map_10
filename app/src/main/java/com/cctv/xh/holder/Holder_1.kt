package com.cctv.xh.holder

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.cctv.xh.R
import com.cctv.xh.entity.DataEntity
import com.cctv.xh.http.tag
import com.cctv.xh.ui.Details
import com.cctv.xh.ui.Preview
import zhan.auto_adapter.AutoHolder

class Holder_1(itemView: View?, data: MutableMap<String, Any>?) :
    AutoHolder<DataEntity>(itemView, data) {

    private var image: ImageView = itemView!!.findViewById(R.id.image)
    private var itemTv: TextView = itemView!!.findViewById(R.id.itemTv)

    override fun bind(p0: Int, p1: DataEntity?) {
        val url = "https://geo0.ggpht.com/cbk?output=thumbnail&thumb=2&panoid=" + p1!!.panoid
        Glide.with(itemView.context).load(url).into(image)
        itemTv.text = p1.title
        image.setOnClickListener {
            if (tag == "1"){
                val i = Intent(itemView.context,Details::class.java)
                i.putExtra("data",p1)
                itemView.context.startActivity(i)
            }else{
                val i = Intent(itemView.context,Preview::class.java)
                i.putExtra("url",p1.imageUrl)
                itemView.context.startActivity(i)
            }
        }
    }
}