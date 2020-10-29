package com.stefanenko.gitphone.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stefanenko.gitphone.data.database.Database
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun provideDatabase(context: Application): RoomDatabase {
        return Room.databaseBuilder(context, Database::class.java, "git-repository-local").build()
    }
}