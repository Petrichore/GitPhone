package com.stefanenko.gitphone.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithRepo(
    @Embedded
    val user: User,

    @Relation(parentColumn = "id_user", entityColumn = "id_user_user")
    val repositoryList: List<Repository>
)