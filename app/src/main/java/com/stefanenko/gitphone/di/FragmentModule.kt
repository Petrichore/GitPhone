package com.stefanenko.gitphone.di

import com.stefanenko.gitphone.ui.fragment.enterToRepository.GitUsernameFragment
import com.stefanenko.gitphone.ui.fragment.repositoryList.EmptyRepositoriesFragment
import com.stefanenko.gitphone.ui.fragment.repositoryList.RepositoryListFragment
import com.stefanenko.gitphone.ui.fragment.savedRepositories.SavedRepositoriesFragment
import com.stefanenko.gitphone.ui.fragment.start.StartScreenFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector()
    abstract fun provideStartScreenFragment(): StartScreenFragment

    @ContributesAndroidInjector()
    abstract fun provideRepositoryListFragment(): RepositoryListFragment

    @ContributesAndroidInjector()
    abstract fun provideEmptyRepositoriesFragment(): EmptyRepositoriesFragment

    @ContributesAndroidInjector()
    abstract fun provideEmptySavedRepositoriesFragment(): SavedRepositoriesFragment

    @ContributesAndroidInjector()
    abstract fun provideGitUsernameFragment(): GitUsernameFragment
}