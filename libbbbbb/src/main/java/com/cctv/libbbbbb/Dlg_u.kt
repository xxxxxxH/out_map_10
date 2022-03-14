package com.cctv.libbbbbb

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.flyco.dialog.widget.base.BaseDialog
import es.dmoral.toasty.Toasty

class Dlg_u(context: Context) : BaseDialog<Dlg_u>(context) {

    private val dd by lazy {
        Dlg_d(context)
    }

    var url = ""

    var noticeMsg = ""

    override fun onCreateView(): View {
        widthScale(0.85f)
        return View.inflate(context, R.layout.dialog_common, null)
    }

    @SuppressLint("CheckResult")
    override fun setUiBeforShow() {
        setCanceledOnTouchOutside(false)
        findViewById<TextView>(R.id.dialogTitle).text = "New Version"
        var msg: ArrayList<String> = ArrayList()
        var s = ""
        if (noticeMsg.contains("|")) {
            msg = noticeMsg.split("|") as ArrayList<String>
            msg.forEach {
                s = "$s$it\n"
            }
        }
        findViewById<TextView>(R.id.dialogContent).text = s
        findViewById<Button>(R.id.dialogBtn).apply {
            text = "Experience"
            setOnClickListener {
                dismiss()
                dd.show()
                var t = 0L
                if (TextUtils.isEmpty(url)) {
                    Toasty.error(context, "no path")
                }
                DownLoadHttpUtils.getInstance().initUrl(url, null)
                    .setFilePath(filePath)
                    .setFileName(fileName)
                    .setActionCallBack(
                        { total ->
                            Log.i(TAG, "total  = $total")
                            t = total
                        }, { progress ->
                            Log.i(TAG, "progress  = ${(progress * 100) / t}")
                            val cp: Int = ((progress * 100) / t).toInt()
                            dd.setProgress(cp)
                        }, { file ->
                            Log.i(TAG, "success  = $file")
                            dd.dismiss()
                            route2Install(context, file)
                        }, { error ->
                            Log.i(TAG, "error  = $error")
                            dd.dismiss()
                            Toasty.error(context, "download error")
                        }).down()
            }
        }
    }

    override fun onBackPressed() {

    }
}