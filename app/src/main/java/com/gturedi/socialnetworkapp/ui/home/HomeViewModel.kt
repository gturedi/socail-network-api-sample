package com.gturedi.socialnetworkapp.ui.home

import androidx.lifecycle.*
import com.gturedi.socialnetworkapp.network.model.NetworkResult
import com.gturedi.socialnetworkapp.network.SocialNetworkRepository
import com.gturedi.socialnetworkapp.network.model.CheckinReponseModel
import com.gturedi.socialnetworkapp.network.model.SocialNetworkResponse
import com.gturedi.socialnetworkapp.util.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: SocialNetworkRepository) : ViewModel() {

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

class HomeViewModelFactory(private val repository: SocialNetworkRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>) = HomeViewModel(repository) as T
}