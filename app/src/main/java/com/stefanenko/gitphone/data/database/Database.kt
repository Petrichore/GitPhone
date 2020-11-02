package com.stefanenko.gitphone.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stefanenko.gitphone.data.database.dao.GitRepositoryDao
import com.stefanenko.gitphone.data.database.entity.Repository
import com.stefanenko.gitphone.data.database.entity.User
import javax.inject.Singleton

@Database(entities = [User::class, Repository::class], version = 2)
abstract class Database : RoomDatabase() {
    abstract fun getRepositoryDao(): GitRepositoryDao
}