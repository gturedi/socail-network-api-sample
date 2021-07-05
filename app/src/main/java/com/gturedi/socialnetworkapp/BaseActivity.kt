package com.gturedi.socialnetworkapp

import android.app.Dialog
import android.view.Window
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    private val progressDialog by lazy { Dialog(this).apply { setTitle(R.string.loading) } }

    fun showLoading(cancelable: Boolean = false) = with(progressDialog) {
        progressDialog.setCancelable(cancelable)
        show()
    }

    fun hideLoading() = progressDialog.dismiss()

}