package com.gturedi.socialnetworkapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.gturedi.socialnetworkapp.network.AuthRepository
import com.gturedi.socialnetworkapp.network.model.NetworkResult
import com.gturedi.socialnetworkapp.util.log
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _authCode = MutableLiveData<String>()
    val authCode: LiveData<String> get() = _authCode

    init {
        log("AuthViewModel init")
    }

    fun updateAuthCode(code: String) {
        _authCode.value = code
    }

    fun handleAuthorizationCode(code: String?) = liveData {
        emit(NetworkResult.Loading)
        val result = repository.retrieveAccessToken(code.orEmpty())
        emit(result)
    }
}