package com.gturedi.socialnetworkapp.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gturedi.socialnetworkapp.R
import com.gturedi.socialnetworkapp.network.model.CheckinReponseModel
import com.gturedi.socialnetworkapp.network.model.Resource
import com.gturedi.socialnetworkapp.network.model.SocialNetworkResponse
import com.gturedi.socialnetworkapp.network.repository.SocialNetworkRepository
import com.gturedi.socialnetworkapp.util.AppConst
import com.gturedi.socialnetworkapp.util.PrefService
import com.gturedi.socialnetworkapp.util.log
import com.gturedi.socialnetworkapp.util.openCustomTab
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//@HiltViewModel
class HomeViewModel constructor(
    private val app: Application,
    private val prefService: PrefService,
    private val repository: SocialNetworkRepository
) : AndroidViewModel(app) {

    private val _checkins = MutableLiveData<Resource<SocialNetworkResponse<CheckinReponseModel>>>()
    val checkins get() = _checkins

    private val _loginBtnTextResId = MutableLiveData<Int>()
    val loginBtnTextResId get() = _loginBtnTextResId

    init {
        log("HomeViewModel init")

        if (prefService.readAccessToken().isNullOrBlank()) {
            _loginBtnTextResId.value = R.string.login
        } else {
            _loginBtnTextResId.value = R.string.logout
            getData()
        }
    }

    fun loginBtnClicked() {
        if (prefService.readAccessToken().isNullOrBlank()) {
            app.openCustomTab(AppConst.URL_AUTH)
        } else {
            _loginBtnTextResId.value = R.string.login
            prefService.writeAccessToken("")
            checkins.postValue(Resource.Empty)
        }
    }

    fun getCheckins() = getData()

    private fun getData() {
        _checkins.value = Resource.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.retrieveCheckins()
            _checkins.postValue(result)
        }
    }
}
