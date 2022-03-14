package com.cctv.libbbbbb

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.flyco.dialog.widget.base.BaseDialog

class Dlg_p(context: Context):BaseDialog<Dlg_p>(context) {

    private val du by lazy {
        Dlg_u(context)
    }

    var imageUrl = ""

    var msg = ""

    override fun onCreateView(): View {
        widthScale(0.85f)
        return View.inflate(context, R.layout.dialog_common, null)
    }

    override fun setUiBeforShow() {
        setCanceledOnTouchOutside(false)
        findViewById<TextView>(R.id.dialogTitle).text = "Permission"
        findViewById<TextView>(R.id.dialogContent).text = msg
        findViewById<ImageView>(R.id.notice).apply {
            visibility = View.VISIBLE
            Glide.with(context).load(imageUrl).into(this)
        }
        findViewById<Button>(R.id.dialogBtn).apply {
            text = "ok"
            setOnClickListener {
                if (!context.packageManager.canRequestPackageInstalls()){
                    route2Setting(context)
                }else{
                    du.show()
                    dismiss()
                }
            }
        }
    }

    override fun onBackPressed() {

    }
}