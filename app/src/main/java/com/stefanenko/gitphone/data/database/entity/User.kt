package com.stefanenko.gitphone.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class User(
    @PrimaryKey
    @ColumnInfo(name = "id_user")
    val userId: Long,

    @ColumnInfo(name = "username")
    val name: String,

    @ColumnInfo(name = "image")
    val image: String
): Serializable