package com.stefanenko.gitphone.ui.fragment.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefanenko.gitphone.data.DataLoadState
import com.stefanenko.gitphone.data.localData.GitRepositoryListParcelable
import com.stefanenko.gitphone.data.repository.DataRepository
import com.stefanenko.gitphone.ui.singleEvent.SingleEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class StartScreenViewModel @Inject constructor(private val repository: DataRepository) :
    ViewModel() {

    private val _repositoryListLiveData =
        MutableLiveData<SingleEvent<GitRepositoryListParcelable>>()
    val repositoryListLiveData: LiveData<SingleEvent<GitRepositoryListParcelable>>
        get() = _repositoryListLiveData

    private val _validationErrorLiveData = MutableLiveData<SingleEvent<String>>()
    val validationErrorLiveData: LiveData<SingleEvent<String>>
        get() = _validationErrorLiveData

    private val _loadErrorLiveData = MutableLiveData<SingleEvent<String>>()
    val loadErrorLiveData: LiveData<SingleEvent<String>>
        get() = _validationErrorLiveData

    private val _navigateToEmptyScreen = MutableLiveData<SingleEvent<String>>()
    val navigateToEmptyScreen: LiveData<SingleEvent<String>>
        get() = _navigateToEmptyScreen

    fun fetchGitRepositoryList(username: String) {
        val validationResult = validateUserName(username)
        if (validationResult) {
            viewModelScope.launch {
                val dataLoadState = repository.fetchGitRepositories(username)
                when (dataLoadState) {
                    is DataLoadState.Data -> {
                        val repositoriesList = dataLoadState.data

                        if(repositoriesList.isNotEmpty()){
                            _repositoryListLiveData.value =
                                SingleEvent(GitRepositoryListParcelable(repositoriesList))
                        }else{
                            _navigateToEmptyScreen.value = SingleEvent(username)
                        }
                    }

                    is DataLoadState.LoadError -> {
                        _loadErrorLiveData.value = SingleEvent(dataLoadState.error)
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