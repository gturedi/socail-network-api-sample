package com.gturedi.socialnetworkapp.ui.home

import androidx.lifecycle.ViewModel
import com.gturedi.socialnetworkapp.network.model.NetworkResult
import com.gturedi.socialnetworkapp.network.DataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow

class AuthViewModel : ViewModel() {

    private val dataRepository by lazy { DataRepository() }

    private val _authCode = MutableStateFlow("")
    public val authCode = _authCode.asStateFlow()

    fun updateAuthCode(code:String) {
        _authCode.value = code
    }

    fun handleAuthorizationCode(code: String) = flow {
        if (code.isNullOrBlank()) {
            emit(NetworkResult.Failure("code empty"))
        } else {
            emit(NetworkResult.Loading)
            val result = dataRepository.retrieveAccessToken(code)
            emit(result)
        }
    }
}