package com.stefanenko.gitphone.ui.fragment.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefanenko.gitphone.data.DataLoadState
import com.stefanenko.gitphone.data.dto.gitRepository.GitRepository
import com.stefanenko.gitphone.data.repository.DataRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class StartScreenViewModel @Inject constructor(private val repository: DataRepository) : ViewModel() {

    private val _repositoryListLiveData = MutableLiveData<List<GitRepository>>()
    val repositoryListLiveData: LiveData<List<GitRepository>>
        get() = _repositoryListLiveData

    private val _validationErrorLiveData = MutableLiveData<String>()
    val validationErrorLiveData: LiveData<String>
        get() = _validationErrorLiveData

    private val _loadErrorLiveData = MutableLiveData<String>()
    val loadErrorLiveData: LiveData<String>
        get() = _validationErrorLiveData

    suspend fun fetchGitRepositoryList(username: String) {
        val validationResult = validateUserName(username)
        if(validationResult){
            viewModelScope.launch {
                val dataLoadState = repository.fetchGitRepositories(username)

                when(dataLoadState){
                    is DataLoadState.Data->{
                        _repositoryListLiveData.value = dataLoadState.data.repositoryList
                    }

                    is DataLoadState.LoadError->{
                        _loadErrorLiveData.value = dataLoadState.error
                    }

                    else->{
                        _loadErrorLiveData.value = "No such state exception"
                    }
                }
            }
        }else{
            _validationErrorLiveData.value = "User name can't be blank"
        }
    }

    private fun validateUserName(username: String): Boolean {
        return username.isNotEmpty()
    }
}