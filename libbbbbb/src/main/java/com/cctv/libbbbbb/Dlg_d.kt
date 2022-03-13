package com.cctv.libbbbbb

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.flyco.dialog.widget.base.BaseDialog

class Dlg_d(context: Context) : BaseDialog<Dlg_d>(context) {

    var currentTv: TextView? = null
    var progressBar: ProgressBar? = null

    override fun onCreateView(): View {
        widthScale(0.85f)
        return View.inflate(context, R.layout.dialog_common, null)
    }

    override fun setUiBeforShow() {
        setCanceledOnTouchOutside(false)
        findViewById<TextView>(R.id.dialogTitle).text = "Download"
        findViewById<TextView>(R.id.dialogContent).apply {
            visibility = View.GONE
        }
        findViewById<LinearLayout>(R.id.dialogProgressLl).apply {
            visibility = View.VISIBLE
        }
        findViewById<Button>(R.id.dialogBtn).apply {
            visibility = View.GONE
        }
        currentTv = findViewById(R.id.downloadTv)
        progressBar = findViewById(R.id.progress)
    }

    @SuppressLint("SetTextI18n")
    fun setProgress(progress: Int) {
        currentTv?.let {
            it.text = "current: $progress%"
        }
        progressBar?.let {
            it.progress = progress
        }
    }

    override fun onBackPressed() {

    }
}