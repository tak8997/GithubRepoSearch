package com.tak8997.githubreposearch.ui

import androidx.lifecycle.*
import com.tak8997.githubreposearch.SingleLiveEvent
import com.tak8997.githubreposearch.data.Result
import com.tak8997.githubreposearch.data.model.Repo
import com.tak8997.githubreposearch.data.repository.RepoRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

private const val REPO_QUERY = "repo_query"
private const val SEARCH_TIMEOUT = 250L

class RepoSearchViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: RepoRepository
) : ViewModel() {

    private val query = savedStateHandle.getLiveData<Pair<String, Int>>(REPO_QUERY)

    val repos = MutableLiveData<List<Repo>>(emptyList())
    val loading = MutableLiveData(false)
    val error = SingleLiveEvent<String>()

    init {
        viewModelScope.launch {
            query.asFlow()
                .filter { (query, _) ->
                    if (query.isEmpty()) {
                        repos.value = emptyList()
                        return@filter false
                    }
                    true
                }
                .debounce(SEARCH_TIMEOUT)
                .collect { (query, page) ->
                    searchRepos(query, page)
                }
        }
    }

    fun onRepoTitleEdited(title: String, page: Int) {
        savedStateHandle[REPO_QUERY] = Pair(title, page)
    }

    fun fetchMore(query: String, page: Int) {
        searchRepos(query, page)
    }

    fun onSaveInstanceState(items: MutableList<Repo>) {
        repos.value = items
    }

    private fun searchRepos(query: String, page: Int) {
        viewModelScope.launch {
            when (val repoResult = repository.fetchRepos(query, page)) {
                is Result.Success -> {
                    repos.value = repoResult.data ?: emptyList()
                }
                is Result.Error -> {
                    error.value = repoResult.exception.message
                }
            }
        }
    }
}