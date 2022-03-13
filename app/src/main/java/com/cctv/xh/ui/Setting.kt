package com.cctv.xh.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cctv.xh.R
import kotlinx.android.synthetic.main.activity_setting.*

class Setting : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        settingCloseIv.setOnClickListener(this)
        settingRateRl.setOnClickListener(this)
        settingAboutRl.setOnClickListener(this)
        settingShareRl.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.settingCloseIv -> {
                finish()
            }
            R.id.settingRateRl -> {

            }
            R.id.settingAboutRl -> {

            }
            R.id.settingShareRl -> {
                sendEmil("Life is a fking moving")
            }
        }
    }

    private fun sendEmil(text: String) {
        val reveiverString = arrayOf("nuclearvpnp@outlook.com")
        val ccString = arrayOf<String>()
        val subjectString = "feedBook"
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, reveiverString)
        intent.putExtra(Intent.EXTRA_CC, ccString)
        intent.putExtra(Intent.EXTRA_SUBJECT, subjectString)
        intent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(intent, "Choose Email Client"))
    }
}