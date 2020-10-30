package com.stefanenko.gitphone.domain

import com.stefanenko.gitphone.data.dto.DataLoadState
import com.stefanenko.gitphone.data.dto.gitRepository.GitRepository
import com.stefanenko.gitphone.util.toRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val remoteService: RemoteDataService,
    private val databaseService: DatabaseService
) {

    suspend fun fetchGitRepositories(username: String): DataLoadState<List<GitRepository>> {
        return remoteService.fetchRepository(username)
    }

    suspend fun getCachedRepositories(): DataLoadState<List<GitRepository>> {
        return databaseService.getAllRepositoriesWithUsers()
    }

    suspend fun insertNewRepository(gitRepository: GitRepository): DataLoadState<Boolean> {
        val repository = gitRepository.toRepository()
        return databaseService.insertNewRepository(repository)
    }

    suspend fun fetchAllSavedRepositories(): DataLoadState<List<GitRepository>> {
        return databaseService.getAllRepositories()
    }
}