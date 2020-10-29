package com.stefanenko.gitphone.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.stefanenko.gitphone.data.database.entity.UserWithRepo
import com.stefanenko.gitphone.data.dto.gitRepository.GitRepository

@Dao
interface RepositoryDao {

    @Transaction
    @Query("select * from User")
    fun getUserWithRepositories(): List<UserWithRepo>
}