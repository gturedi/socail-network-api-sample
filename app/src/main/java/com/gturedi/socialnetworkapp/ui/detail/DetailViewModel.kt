package com.gturedi.socialnetworkapp.ui.detail

import androidx.lifecycle.ViewModel
import com.gturedi.socialnetworkapp.network.DataRepository
import com.gturedi.socialnetworkapp.network.model.NetworkResult
import kotlinx.coroutines.flow.flow

class DetailViewModel : ViewModel() {

    private val dataRepository by lazy { DataRepository() }

    fun retrieveVenue(id: String) = flow {
        emit(NetworkResult.Loading)
        val result = dataRepository.retrieveVenue(id)
        emit(result)
    }
}