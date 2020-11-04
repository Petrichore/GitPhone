package com.stefanenko.gitphone.ui.fragment.savedRepositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefanenko.gitphone.ui.singleEvent.SingleEvent
import com.stefanenko.gitphone.domain.util.toRepositoryWithOwnerList
import kotlinx.coroutines.launch
import javax.inject.Inject

class SavedRepositoriesViewModel @Inject constructor(private val dataRepository: com.stefanenko.gitphone.domain.DataRepository) :
    ViewModel() {

    private val _loadErrorLiveData = MutableLiveData<SingleEvent<String>>()
    val loadErrorLiveData: LiveData<SingleEvent<String>>
        get() = _loadErrorLiveData

    private val _repositoryListLiveData = MutableLiveData<List<com.stefanenko.gitphone.domain.entity.RepositoryWithOwner>>()
    val repositoryListLiveData: LiveData<List<com.stefanenko.gitphone.domain.entity.RepositoryWithOwner>>
        get() = _repositoryListLiveData

    private val _repoDeleteResponse = MutableLiveData<String>()
    val repoDeleteResponse: LiveData<String>
        get() = _repoDeleteResponse

    fun fetchSavedRepositoriesWithUser() {
        viewModelScope.launch {
            val dataLoadState = dataRepository.getSavedRepositoriesWithUser()

            when (dataLoadState) {
                is com.stefanenko.gitphone.data.dto.DataResponseState.Data -> {
                    val repositoryOwnerList = dataLoadState.data
                    _repositoryListLiveData.value = repositoryOwnerList.toRepositoryWithOwnerList()
                }

                is com.stefanenko.gitphone.data.dto.DataResponseState.Error -> {
                    _loadErrorLiveData.value = SingleEvent(dataLoadState.error)
                }
            }
        }
    }

    fun deleteSavedRepository(repoId: Long) {
        viewModelScope.launch {
            val dataLoadState = dataRepository.deleteGitRepository(repoId)

            when (dataLoadState) {
                is com.stefanenko.gitphone.data.dto.DataResponseState.Data -> {
                    _repoDeleteResponse.value = "Repo with id $repoId successfully deleted"
                    _repositoryListLiveData.value = dataLoadState.data.toRepositoryWithOwnerList()
                }

                is com.stefanenko.gitphone.data.dto.DataResponseState.Error -> {
                    _loadErrorLiveData.value = SingleEvent(dataLoadState.error)
                }
            }
        }
    }
}