package com.stefanenko.gitphone.di

import com.stefanenko.gitphone.data.repository.DataRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindDataRepository(dataRepository: DataRepository): DataRepository

}