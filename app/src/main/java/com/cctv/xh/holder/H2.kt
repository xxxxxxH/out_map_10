package com.cctv.xh.holder

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.View
import android.widget.TextView
import com.cctv.xh.R
import com.cctv.xh.http.COLORS
import zhan.auto_adapter.AutoHolder

class H2(itemView:View?,data: MutableMap<String, Any>?):AutoHolder<String>(itemView,data) {
    private var tv:TextView = itemView!!.findViewById(R.id.tv)
    override fun bind(p0: Int, p1: String?) {
        tv.text = p1
        val index = (0..15).random()
        tv.setBackgroundColor(Color.parseColor(COLORS[index]))
        tv.setOnClickListener {
            val i = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    "http://maps.google.com/maps?q=${p1}&hl=en"
                )
            )
            i.setPackage("com.google.android.apps.maps")
            itemView.context.startActivity(i)
        }
    }
}