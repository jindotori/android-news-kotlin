package com.dotori.news

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.dotori.news.ui.main.MainActivity
import com.dotori.news.ui.main.fragment.BaseActivity

class SplashActivity : AppCompatActivity() {
    companion object {
        private const val SPLASH_DELAY = 1000L
    }

    override fun finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({splashHandler()}, SPLASH_DELAY)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private fun splashHandler() {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish() // 로딩페이지 Activity stack에서 제거
    }
}