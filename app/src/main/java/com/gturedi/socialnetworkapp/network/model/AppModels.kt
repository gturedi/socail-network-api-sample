package com.gturedi.socialnetworkapp.network.model

sealed class Resource<out T> {

    data class Success<T>(val data: T?) : Resource<T>()
    data class Failure(val message: String?) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
    object Empty : Resource<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Loading -> "Loading[]"
            is Success -> "Success[data: $data]"
            is Failure -> "Failure[error: $message"
            is Empty -> "Empty[]"
        }
    }
}

data class SocialNetworkResponse<T>(
    val meta: MetaModel = MetaModel(0, ""),
    val response: T
)

data class MetaModel(
    val code: Int,
    val errorDetail: String?
)

