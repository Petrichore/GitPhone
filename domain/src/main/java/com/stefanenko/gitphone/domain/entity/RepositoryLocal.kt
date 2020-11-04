package com.stefanenko.gitphone.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepositoryLocal(
    val repoId: Long,

    val name: String,

    val language: String,

    val description: String,

    var isCached: Boolean
) : Parcelable