package com.stefanenko.gitphone.ui.fragment.savedRepositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefanenko.gitphone.data.dto.DataResponseState
import com.stefanenko.gitphone.data.dto.gitRepository.GitRepository
import com.stefanenko.gitphone.domain.DataRepository
import com.stefanenko.gitphone.domain.entity.RepositoryOwner
import com.stefanenko.gitphone.ui.singleEvent.SingleEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class SavedRepositoriesViewModel @Inject constructor(private val dataRepository: DataRepository) :
    ViewModel() {

    private val _loadErrorLiveData = MutableLiveData<SingleEvent<String>>()
    val loadErrorLiveData: LiveData<SingleEvent<String>>
        get() = _loadErrorLiveData

    private val _repositoryListLiveData =
        MutableLiveData<SingleEvent<List<RepositoryOwner>>>()
    val repositoryListLiveData: LiveData<SingleEvent<List<RepositoryOwner>>>
        get() = _repositoryListLiveData

    fun fetchSavedRepositoriesWithUser() {
        viewModelScope.launch {
            val dataLoadState = dataRepository.getSavedRepositoriesWithUser()

            when (dataLoadState) {
                is DataResponseState.Data -> {
                    _repositoryListLiveData.value = SingleEvent(dataLoadState.data)
                }

                is DataResponseState.Error -> {
                    _loadErrorLiveData.value = SingleEvent(dataLoadState.error)
                }
            }
        }
    }

}