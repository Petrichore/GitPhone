package com.stefanenko.gitphone.data.repository

import android.util.Log
import com.stefanenko.gitphone.data.DataLoadState
import com.stefanenko.gitphone.data.dto.gitRepository.GitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(private val remoteRepository: RemoteDataService) {

    suspend fun fetchGitRepositories(
        username: String
    ): DataLoadState<List<GitRepository>> {
        return withContext(Dispatchers.IO) {
            return@withContext remoteRepository.fetchRepository(username)
        }
    }
}