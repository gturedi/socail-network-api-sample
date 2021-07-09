package com.gturedi.socialnetworkapp.ui.home

import androidx.lifecycle.*
import com.gturedi.socialnetworkapp.network.AuthRepository
import com.gturedi.socialnetworkapp.network.model.NetworkResult
import com.gturedi.socialnetworkapp.network.SocialNetworkRepository
import com.gturedi.socialnetworkapp.ui.detail.DetailViewModel
import com.gturedi.socialnetworkapp.util.log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _authCode = MutableLiveData<String>()
    val authCode: LiveData<String> get() = _authCode

    init {
        log("AuthViewModel init")
    }

    fun updateAuthCode(code:String) {
        _authCode.value = code
    }

    fun handleAuthorizationCode(code: String?) = liveData {
        emit(NetworkResult.Loading)
        val result = repository.retrieveAccessToken(code.orEmpty())
        emit(result)
    }
}

class AuthViewModelFactory(private val repository: AuthRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) = AuthViewModel(repository) as T
}