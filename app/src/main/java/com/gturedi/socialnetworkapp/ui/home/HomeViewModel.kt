package com.gturedi.socialnetworkapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gturedi.socialnetworkapp.network.model.CheckinReponseModel
import com.gturedi.socialnetworkapp.network.model.Resource
import com.gturedi.socialnetworkapp.network.model.SocialNetworkResponse
import com.gturedi.socialnetworkapp.network.repository.SocialNetworkRepository
import com.gturedi.socialnetworkapp.util.PrefService
import com.gturedi.socialnetworkapp.util.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val prefService: PrefService,
    private val repository: SocialNetworkRepository
) : ViewModel() {

    private val _checkins = MutableLiveData<Resource<SocialNetworkResponse<CheckinReponseModel>>>()
    val checkins get() = _checkins

    init {
        log("HomeViewModel init")

        if (prefService.readAccessToken().isNullOrBlank().not()) {
            getData()
        }
    }

    fun retrieveCheckins() = getData()

    private fun getData() {
        _checkins.value = Resource.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.retrieveCheckins()
            _checkins.postValue(result)
        }
    }
}
