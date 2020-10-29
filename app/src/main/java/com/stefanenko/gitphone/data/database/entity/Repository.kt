package com.stefanenko.gitphone.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
//    foreignKeys = [ForeignKey(
//        entity = User::class,
//        parentColumns = ["id_user"],
//        childColumns = ["id_user_user"]
//    )]
)
data class Repository(
    @PrimaryKey
    @ColumnInfo(name = "id_repository")
    val repoId: Int,

    @ColumnInfo(name = "id_user_user")
    val userId: Int,

    @ColumnInfo(name = "repository_name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String
) : Serializable