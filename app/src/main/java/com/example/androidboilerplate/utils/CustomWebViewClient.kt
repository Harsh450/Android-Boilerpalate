package com.example.androidboilerplate.utils

import android.graphics.Bitmap
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.androidboilerplate.core.BaseActivity

class CustomWebViewClient(private val activity: BaseActivity) :
    WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        view?.loadUrl(request?.url.toString())
        return true
    }

    override fun onReceivedError(
        view: WebView,
        request: WebResourceRequest,
        error: WebResourceError
    ) {
        activity.showLongToast("Got Error! $error")
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        activity.showProgress()
        super.onPageStarted(view, url, favicon)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        activity.hideProgress()
        super.onPageFinished(view, url)
    }
}
