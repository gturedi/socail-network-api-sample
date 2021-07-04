package com.gturedi.socialnetworkapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val dataRepository by lazy { DataRepository() }

    fun handleAuthorizationCode(code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = dataRepository.retrieveAccessToken(code)
        }
    }
}