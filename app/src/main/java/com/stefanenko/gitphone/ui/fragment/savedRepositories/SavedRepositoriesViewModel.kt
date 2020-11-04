package com.stefanenko.gitphone.ui.fragment.savedRepositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefanenko.gitphone.data.dto.DataResponseState
import com.stefanenko.gitphone.domain.DataRepository
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

    private val _repositoryListLiveData = MutableLiveData<List<RepositoryWithOwner>>()
    val repositoryListLiveData: LiveData<List<RepositoryWithOwner>>
        get() = _repositoryListLiveData

    private val _repoDeleteResponse = MutableLiveData<String>()
    val repoDeleteResponse: LiveData<String>
        get() = _repoDeleteResponse

    private val _inProgress = MutableLiveData<Boolean>()
    val inProgress: LiveData<Boolean>
        get() = _inProgress

    fun fetchSavedRepositoriesWithUser() {
        _inProgress.value = true

        viewModelScope.launch {
            val dataLoadState = dataRepository.getSavedRepositoriesWithUser()
            _inProgress.value = false

            when (dataLoadState) {
                is DataResponseState.Data -> {
                    val repositoryOwnerList = dataLoadState.data
                    _repositoryListLiveData.value = repositoryOwnerList.toRepositoryWithOwnerList()
                }

                is DataResponseState.Error -> {
                    _loadErrorLiveData.value = SingleEvent(dataLoadState.error)
                }
            }
        }
    }

    fun deleteSavedRepository(repoId: Long) {
        viewModelScope.launch {
            val dataLoadState = dataRepository.deleteGitRepository(repoId)

            when (dataLoadState) {
                is DataResponseState.Data -> {
                    _repoDeleteResponse.value = "Repo with id $repoId successfully deleted"
                    _repositoryListLiveData.value = dataLoadState.data.toRepositoryWithOwnerList()
                }

                is DataResponseState.Error -> {
                    _loadErrorLiveData.value = SingleEvent(dataLoadState.error)
                }
            }
        }
    }
}