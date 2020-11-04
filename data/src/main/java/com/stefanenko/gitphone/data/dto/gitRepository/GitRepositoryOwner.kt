package com.stefanenko.gitphone.data.dto.gitRepository

import com.google.gson.annotations.SerializedName

data class GitRepositoryOwner(

    @SerializedName("id")
    val userId: Long,

    @SerializedName("login")
    val ownerName: String,

    @SerializedName("avatar_url")
    val avatarUrl: String
)