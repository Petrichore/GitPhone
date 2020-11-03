package com.stefanenko.gitphone.data.network.api

import com.stefanenko.gitphone.data.dto.gitRepository.GitRepository
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiRepository {

    @GET("users/{username}/repos")
    suspend fun getGitRepository(@Path("username") username: String): Response<List<GitRepository>>

}