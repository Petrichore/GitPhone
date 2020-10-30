package com.stefanenko.gitphone.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.stefanenko.gitphone.ui.ViewModelFactory
import com.stefanenko.gitphone.ui.fragment.savedRepositories.SavedRepositoriesViewModel
import com.stefanenko.gitphone.ui.fragment.repositoryList.RepositoryListViewModel
import com.stefanenko.gitphone.ui.fragment.enterToRepository.GitUsernameViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(GitUsernameViewModel::class)
    abstract fun bindStartScreenViewModel(viewModel: GitUsernameViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RepositoryListViewModel::class)
    abstract fun bindRepositoryListViewModel(viewModel: RepositoryListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SavedRepositoriesViewModel::class)
    abstract fun bindCachedRepositoriesViewModel(viewModel: SavedRepositoriesViewModel): ViewModel
}