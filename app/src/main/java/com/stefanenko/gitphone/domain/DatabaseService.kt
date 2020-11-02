package com.stefanenko.gitphone.domain

import android.util.Log
import com.stefanenko.gitphone.data.database.dao.GitRepositoryDao
import com.stefanenko.gitphone.data.database.entity.Repository
import com.stefanenko.gitphone.data.dto.DataLoadState
import com.stefanenko.gitphone.data.dto.gitRepository.GitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class DatabaseService @Inject constructor(private val gitRepositoryDao: GitRepositoryDao) {

    suspend fun getAllRepositoriesWithUsers(): DataLoadState<List<GitRepository>> {
        return withContext(Dispatchers.IO) {
            try {
                val databaseResponse = gitRepositoryDao.getUserWithRepositories()
                Log.d("database response:", "$databaseResponse")
                DataLoadState.Data(arrayListOf())
            } catch (e: Exception) {
                e.printStackTrace()
                DataLoadState.LoadError("Database error")
            }
        }
    }

    suspend fun insertNewRepository(repository: Repository): DataLoadState<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                gitRepositoryDao.insertNewRepository(repository)
                DataLoadState.Data(true)
            } catch (e: Exception) {
                e.printStackTrace()
                DataLoadState.LoadError("InsertError")
            }
        }
    }

    suspend fun getAllRepositories(): DataLoadState<List<GitRepository>>{
        return withContext(Dispatchers.IO) {
            try {
                val databaseResponse = gitRepositoryDao.getRepositories()
                Log.d("database response:", "$databaseResponse")
                DataLoadState.Data(arrayListOf())
            } catch (e: Exception) {
                e.printStackTrace()
                DataLoadState.LoadError("Database error")
            }
        }
    }
}