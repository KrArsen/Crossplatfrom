package com.example.lab6.di

import org.koin.dsl.module
import com.example.lab6.AboutViewModel
import com.example.lab6.repository.AboutRepository
import com.example.lab6.repository.AboutRepositoryImpl

val appModule = module {
    // Repository
    single<AboutRepository> { AboutRepositoryImpl() }

    // ViewModel
    factory { AboutViewModel(get()) }
}
