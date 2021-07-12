package com.gturedi.socialnetworkapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.gturedi.socialnetworkapp.network.model.Resource
import com.gturedi.socialnetworkapp.network.repository.RemoteSocialNetworkRepository
import com.gturedi.socialnetworkapp.util.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: RemoteSocialNetworkRepository
) : ViewModel() {

    var revenueId = ""

    //private val _revenue = MutableLiveData<NetworkResult<SocialNetworkResponse<VenueResponseModel>>>()
    private val _revenue = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        val result = repository.retrieveVenue(revenueId)
        emit(result)
    }
    val revenue get() = _revenue

    init {
        log("DetailViewModel init")
    }

}