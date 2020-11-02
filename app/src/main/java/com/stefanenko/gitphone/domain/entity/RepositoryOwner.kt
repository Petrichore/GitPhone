package com.stefanenko.gitphone.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepositoryOwner(
    val userId: Long,

    val name: String,

    val imageUrl: String,

    val repositoryList: List<RepositoryLocal>
): Parcelable