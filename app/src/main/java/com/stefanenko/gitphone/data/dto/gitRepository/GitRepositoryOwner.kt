package com.stefanenko.gitphone.data.dto.gitRepository

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitRepositoryOwner(
    @SerializedName("login")
    val ownerName: String,

    @SerializedName("avatar_url")
    val avatarUrl: String,

    @SerializedName("type")
    val type: String
): Parcelable