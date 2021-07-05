package com.gturedi.socialnetworkapp

import androidx.fragment.app.Fragment


open class BaseFragment : Fragment() {

    val customActivity: BaseActivity
        get() = activity as BaseActivity

    fun showLoading(cancelable: Boolean = false) = customActivity.showLoading(cancelable)

    fun hideLoading() = customActivity.hideLoading()
}