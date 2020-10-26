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

class StartScreenViewModel @Inject constructor(private val repository: DataRepository) :
    ViewModel() {

    private val _repositoryListLiveData = MutableLiveData<GitRepository>()
    val repositoryListLiveData: LiveData<GitRepository>
        get() = _repositoryListLiveData

    private val _validationErrorLiveData = MutableLiveData<String>()
    val validationErrorLiveData: LiveData<String>
        get() = _validationErrorLiveData

    suspend fun fetchGitRepositoryList(username: String) {
        val validationResult = validateUserName(username)
        if(validationResult){
            viewModelScope.launch {
                repository.fetchGitRepositories(username)
            }
        }else{
            _validationErrorLiveData.value = "User name can't be blank"
        }
    }

    private fun validateUserName(username: String): Boolean {
        return username.isNotEmpty()
    }
}