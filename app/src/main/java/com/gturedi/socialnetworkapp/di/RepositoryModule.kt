package com.gturedi.socialnetworkapp.di

import com.gturedi.socialnetworkapp.network.repository.AuthRepository
import com.gturedi.socialnetworkapp.network.repository.RemoteAuthRepository
import com.gturedi.socialnetworkapp.network.repository.RemoteSocialNetworkRepository
import com.gturedi.socialnetworkapp.network.repository.SocialNetworkRepository
import org.koin.dsl.module


val repositoryModule = module {
    single { RemoteAuthRepository(get()) as AuthRepository }
    single { RemoteSocialNetworkRepository(get()) as SocialNetworkRepository }
}