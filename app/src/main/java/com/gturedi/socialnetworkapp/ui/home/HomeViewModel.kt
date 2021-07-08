package com.gturedi.socialnetworkapp.ui.home

import androidx.lifecycle.ViewModel
import com.gturedi.socialnetworkapp.network.model.NetworkResult
import com.gturedi.socialnetworkapp.network.SocialNetworkRepository
import kotlinx.coroutines.flow.flow

class HomeViewModel : ViewModel() {

    private val dataRepository by lazy { SocialNetworkRepository() }

    fun retrieveCheckins() = flow {
        emit(NetworkResult.Loading)
        val result = dataRepository.retrieveCheckins()
        emit(result)
    }
}