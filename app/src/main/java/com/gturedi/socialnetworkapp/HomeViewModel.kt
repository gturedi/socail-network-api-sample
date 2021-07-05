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

class HomeViewModel : ViewModel() {

    private val dataRepository by lazy { DataRepository() }

    fun retrieveCheckins() = flow {
        emit(NetworkResult.Loading)
        val result = dataRepository.retrieveCheckins()
        emit(result)
    }
}