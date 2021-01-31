package com.dotori.news.ui.main

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebView.RENDERER_PRIORITY_IMPORTANT
import androidx.databinding.DataBindingUtil
import com.dotori.news.R
import com.dotori.news.databinding.ActivityWebBinding
import com.dotori.news.ui.main.fragment.BaseActivity

class WebActivity: BaseActivity() {
    private lateinit var binding: ActivityWebBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@WebActivity, R.layout.activity_web)

        with(binding) {
            lifecycleOwner = this@WebActivity
            setLayout(this)
        }

        val url = intent.getStringExtra("url")
        showProgressDialog()
        binding.webView.webChromeClient = WebChromeClient()
        binding.webView.setNetworkAvailable(true)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            binding.webView.setRendererPriorityPolicy(RENDERER_PRIORITY_IMPORTANT, true)
        } else {
            binding.webView.settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
        }
        binding.webView.loadUrl(url!!)
    }

    private fun setLayout(binding: ActivityWebBinding) {
        setSupportActionBar(binding.webToolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                //toolbar의 back키 눌렀을 때 동작
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class WebChromeClient : android.webkit.WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            if (newProgress < 100) {
                showProgressDialog()
            }
            if (newProgress == 100) {
                dismissProgressDialog()
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}