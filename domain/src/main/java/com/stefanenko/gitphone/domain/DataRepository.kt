package com.stefanenko.gitphone.domain

import com.stefanenko.gitphone.data.dto.DataResponseState
import com.stefanenko.gitphone.data.exception.DataBaseExceptionConstantStorage.DATA_BASE_ERROR
import com.stefanenko.gitphone.data.service.DatabaseService
import com.stefanenko.gitphone.data.service.RemoteDataService
import com.stefanenko.gitphone.domain.entity.RepositoryLocal
import com.stefanenko.gitphone.domain.entity.RepositoryOwner
import com.stefanenko.gitphone.domain.util.toRepository
import com.stefanenko.gitphone.domain.util.toRepositoryOwner
import com.stefanenko.gitphone.domain.util.toUser
import java.lang.Exception

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val remoteService: RemoteDataService,
    private val databaseService: DatabaseService
) {

    suspend fun getUserGitRepositories(username: String): DataResponseState<RepositoryOwner> {
        return try {
            val gitRepositoryList = remoteService.getRepository(username)
            DataResponseState.Data(gitRepositoryList.toRepositoryOwner())
        } catch (e: Exception) {
            DataResponseState.Error(e.message ?: "")
        }
    }

    suspend fun getSavedRepositoriesWithUser(): DataResponseState<List<RepositoryOwner>> {
        return try {
            val userWithRepoList = databaseService.getAllRepositoriesWithUsers()
            val repositoryWithOwnersList = mutableListOf<RepositoryOwner>()

            userWithRepoList.forEach {
                repositoryWithOwnersList.add(it.toRepositoryOwner())
            }
            DataResponseState.Data(repositoryWithOwnersList)
        } catch (e: Exception) {
            DataResponseState.Error(DATA_BASE_ERROR)
        }
    }

    suspend fun insertNewRepository(
        localRepository: RepositoryLocal,
        userId: Long
    ): DataResponseState<Boolean> {
        val repository = localRepository.toRepository(userId)

        return try {
            val response = databaseService.insertNewRepository(repository)
            DataResponseState.Data(response)
        } catch (e: Exception) {
            DataResponseState.Error(e.message ?: "")
        }
    }

    suspend fun insertNewRepository(
        localRepository: RepositoryLocal,
        repositoryOwner: RepositoryOwner
    ): DataResponseState<Boolean> {
        return try {
            val response = databaseService.addRepositoryOwnerAndCacheRepository(
                localRepository.toRepository(repositoryOwner.ownerId),
                repositoryOwner.toUser()
            )
            DataResponseState.Data(response)
        } catch (e: Exception) {
            DataResponseState.Error(e.message ?: "")
        }
    }

    suspend fun deleteGitRepository(repoId: Long): DataResponseState<List<RepositoryOwner>> {
        return try {
            databaseService.deleteGitRepository(repoId)
            getSavedRepositoriesWithUser()
        } catch (e: Exception) {
            DataResponseState.Error(e.message ?: "")
        }
    }
}