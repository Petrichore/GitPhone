package com.stefanenko.gitphone.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Repository(
    @PrimaryKey
    @ColumnInfo(name = "id_repository")
    val repoId: Long,

    @ColumnInfo(name = "id_user_user")
    val userId: Long,

    @ColumnInfo(name = "repository_name")
    val name: String,

    @ColumnInfo(name = "language")
    val language: String,

    @ColumnInfo(name = "description")
    val description: String
)