package com.stefanenko.gitphone.ui.fragment.enterToRepository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefanenko.gitphone.data.exception.NetworkExceptionConstantStorage.DATA_EMPTY_BODY
import com.stefanenko.gitphone.ui.singleEvent.SingleEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class GitUsernameViewModel @Inject constructor(private val repository: com.stefanenko.gitphone.domain.DataRepository) :
    ViewModel() {

    private val _userRepositoriesLiveData =
        MutableLiveData<SingleEvent<com.stefanenko.gitphone.domain.entity.RepositoryOwner>>()
    val userRepositoriesLiveData: LiveData<SingleEvent<com.stefanenko.gitphone.domain.entity.RepositoryOwner>>
        get() = _userRepositoriesLiveData

    private val _validationErrorLiveData = MutableLiveData<SingleEvent<String>>()
    val validationErrorLiveData: LiveData<SingleEvent<String>>
        get() = _validationErrorLiveData

    private val _loadErrorLiveData = MutableLiveData<SingleEvent<String>>()
    val loadErrorLiveData: LiveData<SingleEvent<String>>
        get() = _loadErrorLiveData

    private val _navigateToEmptyScreen = MutableLiveData<SingleEvent<String>>()
    val navigateToEmptyScreen: LiveData<SingleEvent<String>>
        get() = _navigateToEmptyScreen

    fun fetchGitRepositoryList(username: String) {
        val validationResult = validateUserName(username)
        if (validationResult) {
            viewModelScope.launch {
                val dataLoadState = repository.getUserGitRepositories(username)
                when (dataLoadState) {
                    is com.stefanenko.gitphone.data.dto.DataResponseState.Data -> {
                        _userRepositoriesLiveData.value = SingleEvent((dataLoadState.data))
                    }

                    is com.stefanenko.gitphone.data.dto.DataResponseState.Error -> {
                        when (dataLoadState.error) {
                            DATA_EMPTY_BODY -> {
                                _navigateToEmptyScreen.value = SingleEvent(username)
                            }

                            else -> {
                                _loadErrorLiveData.value = SingleEvent(dataLoadState.error)
                            }
                        }
                    }

                    else -> {
                        _loadErrorLiveData.value = SingleEvent("No such state exception")
                    }
                }
            }
        } else {
            _validationErrorLiveData.value = SingleEvent("User name can't be blank")
        }
    }

    private fun validateUserName(username: String): Boolean {
        return username.isNotEmpty()
    }
}