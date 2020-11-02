package com.stefanenko.gitphone.data.dto.gitRepository

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitRepositoryOwner(

    @SerializedName("id")
    val userId: Long,

    @SerializedName("login")
    val ownerName: String,

    @SerializedName("avatar_url")
    val avatarUrl: String
): Parcelable