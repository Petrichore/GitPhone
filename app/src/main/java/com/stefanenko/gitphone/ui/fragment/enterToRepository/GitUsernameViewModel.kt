package com.stefanenko.gitphone.ui.fragment.enterToRepository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefanenko.gitphone.data.dto.DataResponseState
import com.stefanenko.gitphone.domain.DataRepository
import com.stefanenko.gitphone.domain.entity.RepositoryOwner
import com.stefanenko.gitphone.ui.singleEvent.SingleEvent
import com.stefanenko.gitphone.util.exception.NetworkExceptionConstantStorage.DATA_EMPTY_BODY
import kotlinx.coroutines.launch
import javax.inject.Inject

class GitUsernameViewModel @Inject constructor(private val repository: DataRepository) :
    ViewModel() {

    private val _userRepositoriesLiveData =
        MutableLiveData<SingleEvent<RepositoryOwner>>()
    val userRepositoriesLiveData: LiveData<SingleEvent<RepositoryOwner>>
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
                    is DataResponseState.Data -> {
                        _userRepositoriesLiveData.value = SingleEvent((dataLoadState.data))
                    }

                    is DataResponseState.Error -> {
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