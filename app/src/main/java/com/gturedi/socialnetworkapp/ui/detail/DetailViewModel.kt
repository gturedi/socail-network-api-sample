package com.gturedi.socialnetworkapp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gturedi.socialnetworkapp.network.model.Resource
import com.gturedi.socialnetworkapp.network.model.SocialNetworkResponse
import com.gturedi.socialnetworkapp.network.model.VenueResponseModel
import com.gturedi.socialnetworkapp.network.repository.SocialNetworkRepository
import com.gturedi.socialnetworkapp.util.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//@HiltViewModel
class DetailViewModel
//@AssistedInject
constructor(
    /*@Assisted*/ private val revenueId: String,
    private val repository: SocialNetworkRepository
) : ViewModel() {

    private val _revenue = MutableLiveData<Resource<SocialNetworkResponse<VenueResponseModel>>>()
    val revenue get() = _revenue

    /*companion object {
        fun provideFactory(
            assistedFactory: DetailViewModelFactory,
            revenueId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(revenueId) as T
            }
        }
    }*/

    init {
        log("DetailViewModel init $revenueId")
        getData(revenueId)
    }

    fun getVenue() = getData(revenueId)

    private fun getData(id:String) {
        _revenue.value = Resource.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.retrieveVenue(id)
            _revenue.postValue(result)
        }
    }

    override fun onCleared() {
        log("DetailViewModel onCleared")
        super.onCleared()
    }
}

/*@AssistedFactory
interface DetailViewModelFactory {
    fun create(revenueId: String): DetailViewModel
}*/