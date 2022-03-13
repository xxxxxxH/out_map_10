package com.cctv.xh.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.cctv.xh.R
import com.gjiazhe.panoramaimageview.GyroscopeObserver
import com.roger.catloadinglibrary.CatLoadingView
import kotlinx.android.synthetic.main.activity_preview.*

class Preview : AppCompatActivity() {
    private val url by lazy {
        intent.getStringExtra("url") as String
    }

    private val gyroscopeObserver by lazy {
        GyroscopeObserver()
    }

    private val cat by lazy {
        CatLoadingView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)
        cat.show(supportFragmentManager,"")
        webView.settings.javaScriptEnabled = true;
        webView.settings.javaScriptCanOpenWindowsAutomatically = true;
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true
        webView.settings.useWideViewPort = true
        webView.settings.allowFileAccess = true
        webView.settings.loadsImagesAutomatically = true
        webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        webView.settings.loadWithOverviewMode = true
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                cat.dismiss()
            }
        }
        webView.loadUrl(url)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack()
        }else{
            finish()
        }
    }
}