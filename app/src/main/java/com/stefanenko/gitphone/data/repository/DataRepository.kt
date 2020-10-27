package com.stefanenko.gitphone.data.repository

import com.stefanenko.gitphone.data.DataLoadState
import com.stefanenko.gitphone.data.dto.gitRepository.GitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(private val remoteRepository: RemoteDataService) {

    suspend fun fetchGitRepositories(
        username: String
    ): DataLoadState<List<GitRepository>> {
        return withContext(Dispatchers.IO) {
            return@withContext remoteRepository.fetchRepository(username)
        }
    }

//    suspend fun<R, E> fetchGitRepositories(
//        username: String,
//        doOnResult: (R) -> Unit,
//        doOnError: (E) -> Unit
//    ): DataLoadState<GitRepositoryResponse> {
//        return withContext(Dispatchers.IO) {
//            return@withContext remoteRepository.fetchRepository(username)
//        }
//    }
//
//    private fun <T, R, E> handleDataLoadState(
//        dataLoadState: DataLoadState<T>,
//        doOnResult: (R) -> Unit,
//        doOnError: (E) -> Unit
//    ) {
//        when(dataLoadState){
//            is DataLoadState.Data->{
//
//            }
//
//            is DataLoadState.LoadError->{
//
//            }
//
//            else ->{
//                //TODO throw DataStateException
//            }
//        }
//    }
}