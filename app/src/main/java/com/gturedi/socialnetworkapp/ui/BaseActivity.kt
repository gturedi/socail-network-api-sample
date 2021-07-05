package com.gturedi.socialnetworkapp.ui

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import com.gturedi.socialnetworkapp.R

open class BaseActivity : AppCompatActivity() {

    private val progressDialog by lazy { Dialog(this).apply { setTitle(R.string.loading) } }

    fun showLoading(cancelable: Boolean = false) = with(progressDialog) {
        progressDialog.setCancelable(cancelable)
        show()
    }

    fun hideLoading() = progressDialog.dismiss()

}