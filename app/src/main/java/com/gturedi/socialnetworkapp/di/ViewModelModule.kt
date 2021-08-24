package com.gturedi.socialnetworkapp.di

import com.gturedi.socialnetworkapp.ui.detail.DetailViewModel
import com.gturedi.socialnetworkapp.ui.home.AuthViewModel
import com.gturedi.socialnetworkapp.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { AuthViewModel(get(), get()) }
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { (id: String) -> DetailViewModel(id, get()) }
}