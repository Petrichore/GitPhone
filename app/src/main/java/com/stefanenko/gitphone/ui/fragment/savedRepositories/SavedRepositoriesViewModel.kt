package com.stefanenko.gitphone.ui.fragment.savedRepositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefanenko.gitphone.data.dto.DataResponseState
import com.stefanenko.gitphone.data.dto.gitRepository.GitRepository
import com.stefanenko.gitphone.domain.DataRepository
import com.stefanenko.gitphone.domain.entity.RepositoryOwner
import com.stefanenko.gitphone.domain.entity.RepositoryWithOwner
import com.stefanenko.gitphone.ui.singleEvent.SingleEvent
import com.stefanenko.gitphone.util.toRepositoryWithOwnerList
import kotlinx.coroutines.launch
import javax.inject.Inject

class SavedRepositoriesViewModel @Inject constructor(private val dataRepository: DataRepository) :
    ViewModel() {

    private val _loadErrorLiveData = MutableLiveData<SingleEvent<String>>()
    val loadErrorLiveData: LiveData<SingleEvent<String>>
        get() = _loadErrorLiveData

    private val _repositoryListLiveData =
        MutableLiveData<SingleEvent<List<RepositoryWithOwner>>>()
    val repositoryListLiveData: LiveData<SingleEvent<List<RepositoryWithOwner>>>
        get() = _repositoryListLiveData

    fun fetchSavedRepositoriesWithUser() {
        viewModelScope.launch {
            val dataLoadState = dataRepository.getSavedRepositoriesWithUser()

            when (dataLoadState) {
                is DataResponseState.Data -> {
                    val repositoryOwnerList = dataLoadState.data
                    _repositoryListLiveData.value =
                        SingleEvent(repositoryOwnerList.toRepositoryWithOwnerList())
                }

                is DataResponseState.Error -> {
                    _loadErrorLiveData.value = SingleEvent(dataLoadState.error)
                }
            }
        }
    }

}