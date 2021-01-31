package com.dotori.news.ui.main.fragment

import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import com.dotori.news.R

open class BaseActivity : AppCompatActivity() {

    companion object {
        private const val CLOSE_TIMEOUT = 2000
    }

    private var onBackPressedTime = 0L


    private val progressDialog: AppCompatDialog by lazy {
        AppCompatDialog(this@BaseActivity).apply {
            setContentView(R.layout.dialog_progress)
            window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            setCancelable(false)
        }
    }

    fun showProgressDialog() {
        Log.d("progress", "show")
        if (!progressDialog.isShowing) {
            progressDialog.show()
        }
    }

    fun dismissProgressDialog() {
        Log.d("progress", "dismiss")
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    override fun onBackPressed() {
        when (System.currentTimeMillis() > onBackPressedTime + CLOSE_TIMEOUT) {
            true -> {
                onBackPressedTime = System.currentTimeMillis()
                Toast.makeText(this@BaseActivity, getString(R.string.noti_exit), Toast.LENGTH_SHORT)
                    .show()
            }

            false -> {
                finish()
            }
        }
    }
}