package com.stefanenko.gitphone.domain.entity

import java.io.Serializable

data class RepositoryWithOwner(
    val ownerId: Long,

    val ownerName: String,

    val imageUrl: String,

    val repoId: Long,

    val repoName: String,

    val language: String,

    val description: String,

    var isCached: Boolean
) : Serializable