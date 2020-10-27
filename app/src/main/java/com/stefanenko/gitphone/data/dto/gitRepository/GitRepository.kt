package com.stefanenko.gitphone.data.dto.gitRepository

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitRepository(
    @SerializedName("name")
    val repoName: String,

    @SerializedName("private")
    val isPrivate: Boolean,

    @SerializedName("description")
    val description: String,

    @SerializedName("fork")
    val isFork: Boolean,

    @SerializedName("language")
    val language: String,

    @SerializedName("owner")
    val repoOwnerGit: GitRepositoryOwner
): Parcelable