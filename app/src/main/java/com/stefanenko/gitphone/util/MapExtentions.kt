package com.stefanenko.gitphone.util

import com.stefanenko.gitphone.data.database.entity.Repository
import com.stefanenko.gitphone.data.dto.gitRepository.GitRepository

fun GitRepository.toRepository(): Repository {
    return Repository(repoId, repoOwnerGit.userId, repoName, description)
}