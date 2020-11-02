package com.stefanenko.gitphone.domain

import com.stefanenko.gitphone.data.dto.DataResponseState
import com.stefanenko.gitphone.data.network.RetrofitService
import com.stefanenko.gitphone.data.network.api.ApiRepository
import com.stefanenko.gitphone.domain.entity.RepositoryOwner
import com.stefanenko.gitphone.util.exception.NetworkExceptionConstantStorage.DATA_EMPTY_BODY
import com.stefanenko.gitphone.util.exception.NetworkExceptionConstantStorage.NETWORK_REQUEST_ERROR
import com.stefanenko.gitphone.util.exception.NetworkExceptionConstantStorage.NO_RESPONSE_DATA_EXCEPTION
import com.stefanenko.gitphone.util.exception.NetworkExceptionConstantStorage.UNCHECKED_ERROR
import com.stefanenko.gitphone.util.toRepositoryOwner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataService @Inject constructor(retrofitService: RetrofitService) {

    private val repositoryApiService = retrofitService.createService(ApiRepository::class.java)

    suspend fun getRepository(username: String): DataResponseState<RepositoryOwner> {
        return withContext(Dispatchers.IO) {
            try {
                val response = repositoryApiService.getGitRepository(username)
                val body = handleResponse(response)
                if (body.isNotEmpty()) {
                    return@withContext DataResponseState.Data(body.toRepositoryOwner())
                } else {
                    DataResponseState.Error(DATA_EMPTY_BODY)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                when (e.message) {
                    NO_RESPONSE_DATA_EXCEPTION -> {
                        return@withContext DataResponseState.Error(NO_RESPONSE_DATA_EXCEPTION)
                    }

                    NETWORK_REQUEST_ERROR -> {
                        return@withContext DataResponseState.Error(NETWORK_REQUEST_ERROR)
                    }

                    else -> {
                        return@withContext DataResponseState.Error(UNCHECKED_ERROR)
                    }
                }
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