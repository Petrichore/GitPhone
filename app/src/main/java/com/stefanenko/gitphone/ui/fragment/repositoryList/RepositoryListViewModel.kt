package com.stefanenko.gitphone.ui.fragment.repositoryList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefanenko.gitphone.data.exception.DataBaseExceptionConstantStorage.NO_SUCH_USER_IN_DATABASE
import com.stefanenko.gitphone.ui.singleEvent.SingleEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepositoryListViewModel @Inject constructor(private val dataRepository: com.stefanenko.gitphone.domain.DataRepository) :
    ViewModel() {

    private val _errorLiveData = MutableLiveData<SingleEvent<String>>()
    val errorLiveData: LiveData<SingleEvent<String>>
        get() = _errorLiveData

    private val _successResponseLiveData = MutableLiveData<SingleEvent<Boolean>>()
    val successResponseLiveData: LiveData<SingleEvent<Boolean>>
        get() = _successResponseLiveData

    private val _noOwnersForSavingRepository =
        MutableLiveData<SingleEvent<Pair<com.stefanenko.gitphone.domain.entity.RepositoryLocal, String>>>()
    val noOwnersForSavingRepository: LiveData<SingleEvent<Pair<com.stefanenko.gitphone.domain.entity.RepositoryLocal, String>>>
        get() = _noOwnersForSavingRepository

    fun saveRepository(repository: com.stefanenko.gitphone.domain.entity.RepositoryLocal, userId: Long) {
        viewModelScope.launch {
            val dataResponseState = dataRepository.insertNewRepository(repository, userId)

            when (dataResponseState) {
                is com.stefanenko.gitphone.data.dto.DataResponseState.Data -> {
                    _successResponseLiveData.value = SingleEvent(dataResponseState.data)
                }

                is com.stefanenko.gitphone.data.dto.DataResponseState.Error -> {
                    when (dataResponseState.error) {
                        NO_SUCH_USER_IN_DATABASE -> {
                            _noOwnersForSavingRepository.value =
                                SingleEvent(
                                    Pair(
                                        repository,
                                        "No match owners of this repository in database, add one and saved this repository?"
                                    )
                                )
                        }
                        else -> _errorLiveData.value = SingleEvent(dataResponseState.error)
                    }
                }
            }
        }
    }

    fun addOwnerAndSavedRepository(repository: com.stefanenko.gitphone.domain.entity.RepositoryLocal, repositoryOwner: com.stefanenko.gitphone.domain.entity.RepositoryOwner) {
        viewModelScope.launch {
            val dataResponseState = dataRepository.insertNewRepository(repository, repositoryOwner)

            when (dataResponseState) {
                is com.stefanenko.gitphone.data.dto.DataResponseState.Data -> {
                    _successResponseLiveData.value = SingleEvent(dataResponseState.data)
                }

                is com.stefanenko.gitphone.data.dto.DataResponseState.Error -> {
                    _errorLiveData.value = SingleEvent(dataResponseState.error)
                }
            }
        }
    }
}