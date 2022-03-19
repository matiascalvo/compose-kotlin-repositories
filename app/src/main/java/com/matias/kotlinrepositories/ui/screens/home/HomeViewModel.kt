package com.matias.kotlinrepositories.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kittinunf.result.onFailure
import com.github.kittinunf.result.onSuccess
import com.matias.domain.model.NoInternetConnectionException
import com.matias.domain.model.Repo
import com.matias.domain.repositories.GithubRepository
import com.matias.kotlinrepositories.ui.screens.ScreenStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val FIRST_PAGE = 0

@OptIn(FlowPreview::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: GithubRepository,
    @Named("debounceMs") private val debounceMs: Long,
) : ViewModel() {

    private val repoList = MutableStateFlow<List<Repo>?>(null)
    private val isLoading = MutableStateFlow(false)
    private val screenStatus = MutableStateFlow(ScreenStatus.IDLE)

    private val searchQuery = MutableStateFlow("")
    private var currentPage = 0

    val state: StateFlow<HomeState> = combine(
        searchQuery,
        repoList,
        isLoading,
        screenStatus
    ) { searchQuery, repoList, isLoading, networkStatus ->
        HomeState(
            searchQuery = searchQuery,
            list = repoList ?: emptyList(),
            isLoading = isLoading,
            screenStatus = networkStatus,
            showEmptyState = repoList?.isEmpty() ?: false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = HomeState(),
    )

    init {
        viewModelScope.launch {
            searchQuery
                .debounce {
                    if (it == "") 0L else debounceMs
                }
                .distinctUntilChanged().collect { query ->
                    currentPage = 0
                    loadData(query, false)
                }
        }
    }

    private suspend fun loadData(query: String, append: Boolean) {
        updateCurrentPage(append)
        screenStatus.value = ScreenStatus.LOADING
        isLoading.value = true

        val result = repo.getKotlinRepos(query, currentPage + 1)
        result.onSuccess {
            repoList.value = if (append) {
                (repoList.value ?: emptyList()).plus(it)
            } else {
                it
            }
            screenStatus.value = ScreenStatus.IDLE
        }.onFailure {
            when (it) {
                is NoInternetConnectionException -> screenStatus.value = ScreenStatus.NO_INTERNET
                else -> screenStatus.value = ScreenStatus.ERROR
            }
        }

        isLoading.value = false
    }

    private fun updateCurrentPage(append: Boolean) {
        currentPage = if (append) {
            currentPage + 1
        } else {
            FIRST_PAGE
        }
    }

    fun onSearchUpdated(value: String) {
        searchQuery.value = value
    }

    fun onReachedLastElement() {
        if (!isLoading.value) {
            viewModelScope.launch {
                loadData(searchQuery.value, append = true)
            }
        }
    }
}
