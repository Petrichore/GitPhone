package com.stefanenko.gitphone.domain

import com.stefanenko.gitphone.data.database.dao.GitRepositoryDao
import com.stefanenko.gitphone.data.database.entity.Repository
import com.stefanenko.gitphone.data.database.entity.User
import com.stefanenko.gitphone.data.dto.DataResponseState
import com.stefanenko.gitphone.domain.entity.RepositoryOwner
import com.stefanenko.gitphone.util.exception.DataBaseExceptionConstantStorage.DELETE_ERROR
import com.stefanenko.gitphone.util.exception.DataBaseExceptionConstantStorage.DELETE_ERROR_UNMATCHED_ID
import com.stefanenko.gitphone.util.exception.DataBaseExceptionConstantStorage.INSERT_ERROR
import com.stefanenko.gitphone.util.exception.DataBaseExceptionConstantStorage.NO_SUCH_USER_IN_DATABASE
import com.stefanenko.gitphone.util.exception.DataExceptionConstantStorage.INVALID_STATE
import com.stefanenko.gitphone.util.toRepositoryOwner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class DatabaseService @Inject constructor(private val gitRepositoryDao: GitRepositoryDao) {

    suspend fun getAllRepositoriesWithUsers(): DataResponseState<List<RepositoryOwner>> {
        return withContext(Dispatchers.IO) {
            try {
                val userWithRepoList = gitRepositoryDao.getUserWithRepositories()
                val repositoryWithOwnersList = mutableListOf<RepositoryOwner>()

                userWithRepoList.forEach {
                    repositoryWithOwnersList.add(it.toRepositoryOwner())
                }

                DataResponseState.Data(repositoryWithOwnersList)
            } catch (e: Exception) {
                e.printStackTrace()
                DataResponseState.Error("Database error")
            }
        }
    }

    suspend fun insertNewRepository(repository: Repository): DataResponseState<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val isOwnerExistPair = isRepositoryOwnerExist(repository.userId)
                if (isOwnerExistPair.first) {
                    gitRepositoryDao.insertNewRepository(repository)
                    DataResponseState.Data(true)
                } else {
                    DataResponseState.Error(NO_SUCH_USER_IN_DATABASE)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                DataResponseState.Error(INSERT_ERROR)
            }
        }
    }

    suspend fun addRepositoryOwnerAndCacheRepository(
        repository: Repository,
        user: User
    ): DataResponseState<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                gitRepositoryDao.insertNewUser(user)
                gitRepositoryDao.insertNewRepository(repository)
                DataResponseState.Data(true)
            } catch (e: Exception) {
                e.printStackTrace()
                DataResponseState.Error(INSERT_ERROR)
            }
        }
    }

    suspend fun deleteGitRepository(repoId: Long): DataResponseState<Boolean>{
        return withContext(Dispatchers.IO){
            try {
                val deleteId = gitRepositoryDao.deleteGitRepository(repoId)
                if(deleteId == repoId){
                    DataResponseState.Data(true)
                }else{
                    DataResponseState.Error(DELETE_ERROR_UNMATCHED_ID)
                }
            }catch(e: Exception){
                e.printStackTrace()
                DataResponseState.Error(DELETE_ERROR)
            }
        }
    }


    private suspend fun insertNewUser(user: User): DataResponseState<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val userId = gitRepositoryDao.insertNewUser(user)
                if (userId == user.userId) {
                    DataResponseState.Data(true)
                } else {
                    DataResponseState.Error("InsertError")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                DataResponseState.Error("DatabaseError")
            }
        }
    }

    private suspend fun getUserById(id: Long): DataResponseState<User> {
        return withContext(Dispatchers.IO) {
            try {
                val user = gitRepositoryDao.getUserById(id)
                if (user != null) {
                    DataResponseState.Data(user)
                } else {
                    DataResponseState.Error(NO_SUCH_USER_IN_DATABASE)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                DataResponseState.Error("Database error")
            }
        }
    }

    private suspend fun isRepositoryOwnerExist(userId: Long): Pair<Boolean, String> {
        val dataLoadState = getUserById(userId)

        return when (dataLoadState) {
            is DataResponseState.Data -> {
                Pair(true, "")
            }
            is DataResponseState.Error -> {
                Pair(false, dataLoadState.error)
            }
            else -> {
                Pair(false, INVALID_STATE)
            }
        }
    }
}