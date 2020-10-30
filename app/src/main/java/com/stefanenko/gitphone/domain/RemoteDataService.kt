package com.stefanenko.gitphone.domain

import com.stefanenko.gitphone.data.dto.DataLoadState
import com.stefanenko.gitphone.data.dto.gitRepository.GitRepository
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

    suspend fun fetchRepository(username: String): DataLoadState<List<GitRepository>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = repositoryApiService.fetchGitRepository(username)
                return@withContext handleResponse(response)
            } catch (e: Exception) {
                e.printStackTrace()
                DataLoadState.LoadError("NETWORK ERROR")
            }
        }

    }

    private fun <T> handleResponse(response: Response<T>): DataLoadState<T> {
        return if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                DataLoadState.Data(body)
            } else {
                DataLoadState.LoadError("No response data")
            }
        } else {
            DataLoadState.LoadError("Response error code: ${response.code()}")
        }
    }
}