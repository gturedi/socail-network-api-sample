package com.gturedi.socialnetworkapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.gturedi.socialnetworkapp.network.model.Resource
import com.gturedi.socialnetworkapp.network.repository.AuthRepository
import com.gturedi.socialnetworkapp.util.PrefService
import com.gturedi.socialnetworkapp.util.log

//@HiltViewModel
class AuthViewModel constructor(
    private val prefService: PrefService,
    private val repository: AuthRepository
) : ViewModel() {

    private val _authCode = MutableLiveData<String>()
    val authCode get() = _authCode

    init {
        log("AuthViewModel init")
    }

    fun updateAuthCode(code: String) {
        _authCode.value = code
    }

    fun handleAuthorizationCode(code: String?) = liveData {
        emit(Resource.Loading)
        val result = repository.retrieveAccessToken(code.orEmpty())
        if (result is Resource.Success) {
            prefService.writeAccessToken(result.data?.token.orEmpty())
        }
        emit(result)
    }

}