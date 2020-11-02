package com.stefanenko.gitphone.data.dto.gitRepository

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitRepository(

    @SerializedName("id")
    val repoId: Long,

    @SerializedName("name")
    val repoName: String,

    @SerializedName("description")
    val description: String?,

    @SerializedName("language")
    val language: String?,

    @SerializedName("owner")
    val repoOwnerGit: GitRepositoryOwner
): Parcelable