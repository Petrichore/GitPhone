package com.stefanenko.gitphone.data.service

import com.stefanenko.gitphone.data.dto.gitRepository.GitRepository
import com.stefanenko.gitphone.data.exception.NetworkExceptionConstantStorage.NETWORK_REQUEST_ERROR
import com.stefanenko.gitphone.data.exception.NetworkExceptionConstantStorage.NO_RESPONSE_DATA_EXCEPTION
import com.stefanenko.gitphone.data.network.RetrofitService
import com.stefanenko.gitphone.data.network.api.ApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataService @Inject constructor(retrofitService: RetrofitService) {

    private val repositoryApiService = retrofitService.createService(ApiRepository::class.java)

    suspend fun getRepository(username: String): List<GitRepository> {
        return withContext(Dispatchers.IO) {
            try {
                val response = repositoryApiService.getGitRepository(username)
                return@withContext handleResponse(response)
            } catch (e: Exception) {
                throw e
            }
        }
    }

    private fun <T> handleResponse(response: Response<T>): T {
        return if (response.isSuccessful) {
            response.body() ?: throw Exception(NO_RESPONSE_DATA_EXCEPTION)
        } else {
            throw Exception(NETWORK_REQUEST_ERROR)
        }
    }
}