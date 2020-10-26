package com.stefanenko.gitphone.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(private val remoteRepository: RemoteDataService) {

    suspend fun fetchGitRepositories(username: String) {
        return withContext(Dispatchers.IO) {
            val gitRepositoryList = remoteRepository.fetchRepository(username)

        }
    }
}