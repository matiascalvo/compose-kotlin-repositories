package com.matias.kotlinrepositories.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.matias.domain.model.Repo
import com.matias.domain.usecases.SearchReposUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

@ExperimentalCoroutinesApi
@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchReposUseCase: SearchReposUseCase,
    @Named("debounceMs") private val debounceMs: Long,
) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    var searchResult: Flow<PagingData<Repo>> = _state
        .map { it.query }
        .distinctUntilChanged()
        .onEach { _state.update { it.copy(isDebouncing = true) } }
        .debounce { if (it == "") 0L else debounceMs }
        .flatMapLatest { query ->
            _state.update { it.copy(isDebouncing = false) }
            searchReposUseCase(query = query)
        }.cachedIn(viewModelScope)

    fun onSearchUpdated(value: String) {
        _state.update { it.copy(query = value) }
    }
}
