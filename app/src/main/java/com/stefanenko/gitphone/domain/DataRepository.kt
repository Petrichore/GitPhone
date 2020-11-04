package com.stefanenko.gitphone.domain

import com.stefanenko.gitphone.data.dto.DataResponseState
import com.stefanenko.gitphone.domain.entity.RepositoryLocal
import com.stefanenko.gitphone.domain.entity.RepositoryOwner
import com.stefanenko.gitphone.util.toRepository
import com.stefanenko.gitphone.util.toUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val remoteService: RemoteDataService,
    private val databaseService: DatabaseService
) {

    suspend fun getUserGitRepositories(username: String): DataResponseState<RepositoryOwner> {
        return remoteService.getRepository(username)
    }

    suspend fun getSavedRepositoriesWithUser(): DataResponseState<List<RepositoryOwner>> {
        return databaseService.getAllRepositoriesWithUsers()
    }

    suspend fun insertNewRepository(
        localRepository: RepositoryLocal,
        userId: Long
    ): DataResponseState<Boolean> {
        val repository = localRepository.toRepository(userId)
        return databaseService.insertNewRepository(repository)
    }

    suspend fun insertNewRepository(
        localRepository: RepositoryLocal,
        repositoryOwner: RepositoryOwner
    ): DataResponseState<Boolean> {
        return databaseService.addRepositoryOwnerAndCacheRepository(
            localRepository.toRepository(
                repositoryOwner.ownerId
            ), repositoryOwner.toUser()
        )
    }

    suspend fun deleteGitRepository(
        repoId: Long
    ): DataResponseState<List<RepositoryOwner>> {
        return databaseService.deleteGitRepository(repoId)
    }
}