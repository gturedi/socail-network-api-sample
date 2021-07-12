package com.gturedi.socialnetworkapp.network.repository

import com.gturedi.socialnetworkapp.network.model.Resource
import com.gturedi.socialnetworkapp.network.model.TokenModel

interface AuthRepository {
    suspend fun retrieveAccessToken(code: String): Resource<TokenModel>
}