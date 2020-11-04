package com.stefanenko.gitphone.data.database.dao

import androidx.room.*
import com.stefanenko.gitphone.data.database.entity.Repository
import com.stefanenko.gitphone.data.database.entity.User
import com.stefanenko.gitphone.data.database.entity.UserWithRepo
import com.stefanenko.gitphone.data.dto.gitRepository.GitRepository

@Dao
interface GitRepositoryDao {

    @Transaction
    @Query("select * from User")
    suspend fun getUserWithRepositories(): List<UserWithRepo>

    @Query("select * from User where id_user == :id")
    suspend fun getUserById(id: Long): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewRepository(repository: Repository): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewUser(user: User): Long

    @Query("delete from Repository where id_repository = :repoId")
    suspend fun deleteGitRepository(repoId: Long)

}