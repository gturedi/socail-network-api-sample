package com.gturedi.socialnetworkapp.ui.detail

import androidx.lifecycle.*
import com.gturedi.socialnetworkapp.network.SocialNetworkRepository
import com.gturedi.socialnetworkapp.network.model.CheckinReponseModel
import com.gturedi.socialnetworkapp.network.model.NetworkResult
import com.gturedi.socialnetworkapp.network.model.SocialNetworkResponse
import com.gturedi.socialnetworkapp.network.model.VenueResponseModel
import com.gturedi.socialnetworkapp.util.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: SocialNetworkRepository) : ViewModel() {

    private val _revenue = MutableLiveData<NetworkResult<SocialNetworkResponse<VenueResponseModel>>>()
    val revenue: LiveData<NetworkResult<SocialNetworkResponse<VenueResponseModel>>> get() = _revenue

    init {
        log("DetailViewModel init")
    }

    fun retrieveVenue(id: String) {
        _revenue.value = NetworkResult.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.retrieveVenue(id)
            viewModelScope.launch(Dispatchers.Main) {
                _revenue.value = result
            }
        }
    }
}

class DetailViewModelFactory(private val repository: SocialNetworkRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) = DetailViewModel(repository) as T
}