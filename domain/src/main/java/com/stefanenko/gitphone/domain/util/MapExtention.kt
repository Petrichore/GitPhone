package com.stefanenko.gitphone.domain.util

import com.stefanenko.gitphone.data.database.entity.Repository
import com.stefanenko.gitphone.data.database.entity.User
import com.stefanenko.gitphone.data.database.entity.UserWithRepo
import com.stefanenko.gitphone.data.dto.gitRepository.GitRepository
import com.stefanenko.gitphone.domain.entity.RepositoryOwner
import com.stefanenko.gitphone.domain.entity.RepositoryWithOwner

fun List<GitRepository>.toRepositoryOwner(): RepositoryOwner {
    val repositoryList = mutableListOf<com.stefanenko.gitphone.domain.entity.RepositoryLocal>()
    forEach {
        repositoryList.add(
            com.stefanenko.gitphone.domain.entity.RepositoryLocal(
                it.repoId,
                it.repoName,
                it.language ?: "",
                it.description ?: "",
                false
            )
        )
    }

    with(this[0].repoOwnerGit) {
        return RepositoryOwner(
            userId,
            ownerName,
            avatarUrl,
            repositoryList
        )
    }
}

fun com.stefanenko.gitphone.domain.entity.RepositoryLocal.toRepository(userId: Long): Repository {
    return Repository(repoId, userId, name, language, description)
}

fun RepositoryOwner.toUser(): User {
    return User(ownerId, name, imageUrl)
}

fun UserWithRepo.toRepositoryOwner(): RepositoryOwner {
    val localRepositoryList = mutableListOf<com.stefanenko.gitphone.domain.entity.RepositoryLocal>()

    repositoryList.forEach {
        localRepositoryList.add(
            com.stefanenko.gitphone.domain.entity.RepositoryLocal(
                it.repoId,
                it.name,
                it.language,
                it.description,
                true
            )
        )
    }
    return RepositoryOwner(
        user.userId,
        user.name,
        user.image,
        localRepositoryList
    )
}

fun List<RepositoryOwner>.toRepositoryWithOwnerList(): List<RepositoryWithOwner> {
    val repositoryWithOwnerList = mutableListOf<RepositoryWithOwner>()

    forEach {repoOwner->
        repoOwner.repositoryList.forEach {
            repositoryWithOwnerList.add(
                RepositoryWithOwner(
                    repoOwner.ownerId,
                    repoOwner.name,
                    repoOwner.imageUrl,
                    it.repoId,
                    it.name,
                    it.language,
                    it.description,
                    it.isCached
                )
            )
        }
    }

    return repositoryWithOwnerList
}