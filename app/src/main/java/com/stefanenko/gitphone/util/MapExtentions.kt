package com.stefanenko.gitphone.util

import com.stefanenko.gitphone.data.database.entity.Repository
import com.stefanenko.gitphone.data.database.entity.User
import com.stefanenko.gitphone.data.database.entity.UserWithRepo
import com.stefanenko.gitphone.data.dto.gitRepository.GitRepository
import com.stefanenko.gitphone.domain.entity.RepositoryLocal
import com.stefanenko.gitphone.domain.entity.RepositoryOwner


fun List<GitRepository>.toRepositoryOwner(): RepositoryOwner {
    val repositoryList = mutableListOf<RepositoryLocal>()
    forEach {
        repositoryList.add(
            RepositoryLocal(
                it.repoId,
                it.repoName,
                it.language ?: "",
                it.description ?: "",
                false
            )
        )
    }

    with(this[0].repoOwnerGit) {
        return RepositoryOwner(userId, ownerName, avatarUrl, repositoryList)
    }
}

fun RepositoryLocal.toRepository(userId: Long): Repository {
    return Repository(repoId, userId, name, language, description)
}

fun RepositoryOwner.toUser(): User {
    return User(userId, name, imageUrl)
}

fun UserWithRepo.toRepositoryOwner(): RepositoryOwner {
    val localRepositoryList = mutableListOf<RepositoryLocal>()

    repositoryList.forEach {
        localRepositoryList.add(
            RepositoryLocal(
                it.repoId,
                it.name,
                it.language,
                it.description,
                true
            )
        )
    }
    return RepositoryOwner(user.userId, user.name, user.image, localRepositoryList)
}