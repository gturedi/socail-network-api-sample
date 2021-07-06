package com.gturedi.socialnetworkapp.network.model

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T?) : NetworkResult<T>()
    data class Failure(val message: String?) : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Loading -> "Loading[]"
            is Success -> "Success[data: $data]"
            is Failure -> "Failure[error: $message"
        }
    }
}

data class SocialNetworkResponse<T>(
    val meta: MetaModel,
    val response: T
)

data class MetaModel(
    val code: Int,
    val errorDetail: String?
)

