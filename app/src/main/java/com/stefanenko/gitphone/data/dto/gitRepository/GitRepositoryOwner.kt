package com.stefanenko.gitphone.data.dto.gitRepository

import com.google.gson.annotations.SerializedName

data class GitRepositoryOwner(
    @SerializedName("login")
    val ownerName: String,

    @SerializedName("avatar_url")
    val avatarUrl: String,

    @SerializedName("type")
    val type: String
)