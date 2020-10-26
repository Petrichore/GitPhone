package com.stefanenko.gitphone.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stefanenko.gitphone.ui.ViewModelFactory
import com.stefanenko.gitphone.ui.fragment.start.StartScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @IntoMap
    @Binds
    @ViewModelKey(StartScreenViewModel::class)
    abstract fun bindStartScreenViewModel(viewModel: StartScreenViewModel): ViewModel
}