package com.gturedi.socialnetworkapp.ui

import androidx.fragment.app.Fragment


open class BaseFragment : Fragment() {

    val customActivity: BaseActivity
        get() = activity as BaseActivity

}