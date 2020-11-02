package com.stefanenko.gitphone.ui.fragment.repositoryList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefanenko.gitphone.data.dto.DataResponseState
import com.stefanenko.gitphone.domain.DataRepository
import com.stefanenko.gitphone.domain.entity.RepositoryLocal
import com.stefanenko.gitphone.domain.entity.RepositoryOwner
import com.stefanenko.gitphone.ui.singleEvent.SingleEvent
import com.stefanenko.gitphone.util.exception.DataBaseExceptionConstantStorage.NO_SUCH_USER_IN_DATABASE
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepositoryListViewModel @Inject constructor(private val dataRepository: DataRepository) :
    ViewModel() {

    private val _errorLiveData = MutableLiveData<SingleEvent<String>>()
    val errorLiveData: LiveData<SingleEvent<String>>
        get() = _errorLiveData

    private val _successResponseLiveData = MutableLiveData<SingleEvent<Boolean>>()
    val successResponseLiveData: LiveData<SingleEvent<Boolean>>
        get() = _successResponseLiveData

    private val _noOwnersForSavingRepository =
        MutableLiveData<SingleEvent<Pair<RepositoryLocal, String>>>()
    val noOwnersForSavingRepository: LiveData<SingleEvent<Pair<RepositoryLocal, String>>>
        get() = _noOwnersForSavingRepository

    fun saveRepository(repository: RepositoryLocal, userId: Long) {
        viewModelScope.launch {
            val dataResponseState = dataRepository.insertNewRepository(repository, userId)

            when (dataResponseState) {
                is DataResponseState.Data -> {
                    _successResponseLiveData.value = SingleEvent(dataResponseState.data)
                }

                is DataResponseState.Error -> {
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

    fun addOwnerAndSavedRepository(repository: RepositoryLocal, repositoryOwner: RepositoryOwner) {
        viewModelScope.launch {
            val dataResponseState = dataRepository.insertNewRepository(repository, repositoryOwner)

            when (dataResponseState) {
                is DataResponseState.Data -> {
                    _successResponseLiveData.value = SingleEvent(dataResponseState.data)
                }

                is DataResponseState.Error -> {
                    _errorLiveData.value = SingleEvent(dataResponseState.error)
                }
            }
        }
    }
}