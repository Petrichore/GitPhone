package com.stefanenko.gitphone.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stefanenko.gitphone.data.database.dao.RepositoryDao
import com.stefanenko.gitphone.data.database.entity.Repository
import com.stefanenko.gitphone.data.database.entity.User

@Database(entities = [User::class, Repository::class], version = 0)
abstract class Database : RoomDatabase() {
    abstract fun getRepositoryDao(): RepositoryDao
}