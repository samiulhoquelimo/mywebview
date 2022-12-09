package com.droidturbo.mywebview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.Window
import android.webkit.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.droidturbo.mywebview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var link = "https://www.w3schools.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.webView.canGoBack()) {
                    binding.webView.goBack()
                } else {
                    finish()
                }
            }
        })

        with(binding.webView) {
            try {
                with(this) {
                    with(settings) {
                        javaScriptEnabled = true
                        cacheMode = WebSettings.LOAD_DEFAULT
                        allowContentAccess = true
                        allowFileAccess = true
                        loadWithOverviewMode = false
                        builtInZoomControls = false
                        useWideViewPort = true
                    }
                    webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(
                            view: WebView?, request: WebResourceRequest?
                        ): Boolean {
                            view?.loadUrl(request?.url.toString())
                            return super.shouldOverrideUrlLoading(view, request)
                        }
                    }
                    webChromeClient = object : WebChromeClient() {
                        override fun onProgressChanged(view: WebView?, newProgress: Int) {
                            super.onProgressChanged(view, newProgress)
                            binding.progress.progress = newProgress
                            binding.progress.visibility = when (newProgress) {
                                100 -> View.GONE
                                else -> View.VISIBLE
                            }
                        }
                    }
                    loadUrl(link)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}