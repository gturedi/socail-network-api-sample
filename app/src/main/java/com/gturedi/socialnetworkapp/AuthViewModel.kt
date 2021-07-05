package com.gturedi.socialnetworkapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gturedi.socialnetworkapp.network.NetworkResult
import com.gturedi.socialnetworkapp.network.TokenModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    val dataRepository by lazy { DataRepository() }

    private val _authCode = MutableStateFlow("")
    public val authCode = _authCode.asStateFlow()

    fun updateAuthCode(code:String) {
        _authCode.value = code
    }

    fun handleAuthorizationCode2(code: String) = flow {
        emit(NetworkResult.Loading)
        val result = dataRepository.retrieveAccessToken(code)
        emit(result)
    }
}