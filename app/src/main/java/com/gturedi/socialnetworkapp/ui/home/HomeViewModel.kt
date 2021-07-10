package com.gturedi.socialnetworkapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.gturedi.socialnetworkapp.network.SocialNetworkRepository
import com.gturedi.socialnetworkapp.network.model.Resource
import com.gturedi.socialnetworkapp.util.PrefService
import com.gturedi.socialnetworkapp.util.log
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val prefService: PrefService,
    private val repository: SocialNetworkRepository
) : ViewModel() {

    //private val _checkins = MutableLiveData<NetworkResult<SocialNetworkResponse<CheckinReponseModel>>>()
    private val _checkins = liveData {
        emit(Resource.Loading)
        val result = repository.retrieveCheckins()
        emit(result)
    }
    val checkins get() = _checkins

    init {
        log("HomeViewModel init")

        if (prefService.accessToken().isNullOrBlank().not()) {
            checkins
        }
    }
}
