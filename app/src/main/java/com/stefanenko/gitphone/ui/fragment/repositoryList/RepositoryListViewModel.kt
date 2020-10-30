package com.stefanenko.gitphone.ui.fragment.repositoryList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefanenko.gitphone.data.dto.DataLoadState
import com.stefanenko.gitphone.data.dto.gitRepository.GitRepository
import com.stefanenko.gitphone.domain.DataRepository
import com.stefanenko.gitphone.ui.singleEvent.SingleEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepositoryListViewModel @Inject constructor(private val dataRepository: DataRepository): ViewModel() {

    private val _loadErrorLiveData = MutableLiveData<SingleEvent<String>>()
    val loadErrorLiveData: LiveData<SingleEvent<String>>
        get() = _loadErrorLiveData

    private val _loadSuccessfullyLiveData = MutableLiveData<SingleEvent<Boolean>>()
    val loadSuccessfullyLiveData: LiveData<SingleEvent<Boolean>>
        get() = _loadSuccessfullyLiveData

    fun cacheRepository(repository: GitRepository){
        viewModelScope.launch {
            val dataLoadState = dataRepository.insertNewRepository(repository)

            when(dataLoadState){
                is DataLoadState.Data->{
                    _loadSuccessfullyLiveData.value = SingleEvent(dataLoadState.data)
                }

                is DataLoadState.LoadError->{
                    _loadErrorLiveData.value = SingleEvent(dataLoadState.error)
                }
            }
        }
    }
}