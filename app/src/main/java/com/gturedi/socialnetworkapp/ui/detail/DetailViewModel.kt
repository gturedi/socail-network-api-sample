package com.gturedi.socialnetworkapp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gturedi.socialnetworkapp.network.model.Resource
import com.gturedi.socialnetworkapp.network.model.SocialNetworkResponse
import com.gturedi.socialnetworkapp.network.model.VenueResponseModel
import com.gturedi.socialnetworkapp.network.repository.SocialNetworkRepository
import com.gturedi.socialnetworkapp.util.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: SocialNetworkRepository
) : ViewModel() {

    private val _revenue = MutableLiveData<Resource<SocialNetworkResponse<VenueResponseModel>>>()
    val revenue get() = _revenue

    init {
        log("DetailViewModel init")
    }

    fun getVenue(id:String) = getData(id)

    private fun getData(id:String) {
        _revenue.value = Resource.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.retrieveVenue(id)
            _revenue.postValue(result)
        }
    }
}
