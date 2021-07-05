package com.gturedi.socialnetworkapp.network

import com.google.gson.annotations.SerializedName

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T?) : NetworkResult<T>()
    data class Failure<T>(val error: ServiceError) : NetworkResult<T>()
    object Loading : NetworkResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is NetworkResult.Loading -> "Loading[]"
            is NetworkResult.Success -> "Success[data: $data]"
            is NetworkResult.Failure -> "Failure[error: $error"
        }
    }
}

class ServiceError(val message: String? = null)

data class TokenModel(
    @SerializedName("access_token") val token: String
)