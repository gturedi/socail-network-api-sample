package com.gturedi.socialnetworkapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gturedi.socialnetworkapp.network.SocialNetworkRepository
import com.gturedi.socialnetworkapp.network.model.NetworkResult
import com.gturedi.socialnetworkapp.network.model.SocialNetworkResponse
import com.gturedi.socialnetworkapp.network.model.VenueResponseModel
import com.gturedi.socialnetworkapp.util.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: SocialNetworkRepository
) : ViewModel() {

    private val _revenue = MutableLiveData<NetworkResult<SocialNetworkResponse<VenueResponseModel>>>()
    val revenue: LiveData<NetworkResult<SocialNetworkResponse<VenueResponseModel>>> get() = _revenue

    init {
        log("DetailViewModel init")
    }

    fun retrieveVenue(id: String) {
        _revenue.value = NetworkResult.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.retrieveVenue(id)
            _revenue.postValue(result)
        }
    }
}
