package com.gturedi.socialnetworkapp.network.model

import com.google.gson.annotations.SerializedName

data class TokenModel(
    @SerializedName("access_token") val token: String
)