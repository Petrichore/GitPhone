package com.stefanenko.gitphone.data.network.api

import com.stefanenko.gitphone.data.dto.gitRepository.GitRepository
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiRepository {

    @GET("users/{username}/repos")
    suspend fun fetchGitRepository(@Path("username") username: String): Response<List<GitRepository>>

//    @GET("repos/{username}/{repo}/commits")
//    suspend fun fetchRepositoryCommits(
//        @Path("username") username: String,
//        @Path("repo") repositoryName: String
//    ): Response<List<>>
}