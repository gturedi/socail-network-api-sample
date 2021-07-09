package com.gturedi.socialnetworkapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gturedi.socialnetworkapp.network.SocialNetworkRepository
import com.gturedi.socialnetworkapp.network.model.CheckinReponseModel
import com.gturedi.socialnetworkapp.network.model.NetworkResult
import com.gturedi.socialnetworkapp.network.model.SocialNetworkResponse
import com.gturedi.socialnetworkapp.util.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: SocialNetworkRepository
) : ViewModel() {

    private val _checkins = MutableLiveData<NetworkResult<SocialNetworkResponse<CheckinReponseModel>>>()
    val checkins: LiveData<NetworkResult<SocialNetworkResponse<CheckinReponseModel>>> get() = _checkins

    init {
        log("HomeViewModel init")
    }

    fun retrieveCheckins() {
        _checkins.value = NetworkResult.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.retrieveCheckins()
            viewModelScope.launch(Dispatchers.Main) {
                _checkins.value = result
            }
        }
    }
}