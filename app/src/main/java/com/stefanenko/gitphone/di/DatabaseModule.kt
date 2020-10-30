package com.stefanenko.gitphone.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stefanenko.gitphone.data.database.Database
import com.stefanenko.gitphone.data.database.dao.GitRepositoryDao
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule(context: Application) {

    private val database =
        Room.databaseBuilder(context, Database::class.java, "git-repository-local").build()

    @Provides
    fun providesGitRepositoryDao(): GitRepositoryDao {
        return database.getRepositoryDao()
    }
}