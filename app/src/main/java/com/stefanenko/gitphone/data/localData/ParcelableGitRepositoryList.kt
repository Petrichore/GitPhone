package com.stefanenko.gitphone.data.localData

import android.os.Parcelable
import com.stefanenko.gitphone.data.dto.gitRepository.GitRepository
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ParcelableGitRepositoryList(
    val repositoryList: List<GitRepository>
): Parcelable
