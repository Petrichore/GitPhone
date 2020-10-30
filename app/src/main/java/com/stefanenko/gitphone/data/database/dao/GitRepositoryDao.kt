package com.stefanenko.gitphone.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.stefanenko.gitphone.data.database.entity.Repository
import com.stefanenko.gitphone.data.database.entity.User
import com.stefanenko.gitphone.data.database.entity.UserWithRepo
import com.stefanenko.gitphone.data.dto.gitRepository.GitRepository

@Dao
interface GitRepositoryDao {

    @Transaction
    @Query("select * from User")
    suspend fun getUserWithRepositories(): List<UserWithRepo>

    @Query("select * from Repository")
    suspend fun getRepositories(): List<Repository>

    @Insert
    suspend fun insertNewRepository(repository: Repository)

    @Insert
    suspend fun insertNewUser(user: User)
}