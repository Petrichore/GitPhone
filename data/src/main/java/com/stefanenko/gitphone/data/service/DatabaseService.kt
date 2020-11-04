package com.stefanenko.gitphone.data.service

import com.stefanenko.gitphone.data.database.dao.GitRepositoryDao
import com.stefanenko.gitphone.data.database.entity.Repository
import com.stefanenko.gitphone.data.database.entity.User
import com.stefanenko.gitphone.data.database.entity.UserWithRepo
import com.stefanenko.gitphone.data.dto.DataResponseState
import com.stefanenko.gitphone.data.exception.DataBaseExceptionConstantStorage.DELETE_ERROR
import com.stefanenko.gitphone.data.exception.DataBaseExceptionConstantStorage.GET_USER_ERROR
import com.stefanenko.gitphone.data.exception.DataBaseExceptionConstantStorage.INSERT_ERROR
import com.stefanenko.gitphone.data.exception.DataBaseExceptionConstantStorage.NO_SUCH_USER_IN_DATABASE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class DatabaseService @Inject constructor(private val gitRepositoryDao: GitRepositoryDao) {

    suspend fun getAllRepositoriesWithUsers(): List<UserWithRepo> {
        return withContext(Dispatchers.IO) {
            try {
                gitRepositoryDao.getUserWithRepositories()
            } catch (e: Exception) {
                e.printStackTrace()
                throw Exception()
            }
        }
    }

    suspend fun insertNewRepository(repository: Repository): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val repoOwnerId = getUserById(repository.userId)
                if (repoOwnerId != null) {
                    gitRepositoryDao.insertNewRepository(repository)
                    true
                } else {
                    throw Exception(NO_SUCH_USER_IN_DATABASE)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                throw Exception(INSERT_ERROR)
            }
        }
    }

    suspend fun addRepositoryOwnerAndCacheRepository(repository: Repository, user: User): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val insertedRepoId = gitRepositoryDao.insertNewUser(user)
                val insertedUserId = gitRepositoryDao.insertNewRepository(repository)
                insertedRepoId == repository.repoId && insertedUserId == user.userId
            } catch (e: Exception) {
                e.printStackTrace()
                throw Exception(INSERT_ERROR)
            }
        }
    }

    suspend fun deleteGitRepository(repoId: Long): List<UserWithRepo> {
        return withContext(Dispatchers.IO) {
            try {
                gitRepositoryDao.deleteGitRepository(repoId)
                getAllRepositoriesWithUsers()
            } catch (e: Exception) {
                e.printStackTrace()
                throw Exception(DELETE_ERROR)
            }
        }
    }

    private suspend fun getUserById(id: Long): Long? {
        return withContext(Dispatchers.IO) {
            try {
                val user = gitRepositoryDao.getUserById(id)
                user?.userId
            } catch (e: Exception) {
                e.printStackTrace()
                throw Exception(GET_USER_ERROR)
            }
        }
    }
}