package com.matias.kotlinrepositories.ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.matias.domain.model.Repo
import com.matias.kotlinrepositories.R
import com.matias.kotlinrepositories.ui.composables.EmptyQuerySearchScreen
import com.matias.kotlinrepositories.ui.composables.EmptyResultsSearchScreen
import com.matias.kotlinrepositories.ui.composables.PaginatedRepoList
import com.matias.kotlinrepositories.ui.composables.TopSearchBar

@Composable
fun SearchScreen(
    onDetails: (Repo) -> Unit = {},
    onNavigateUp: () -> Unit = {}
) {
    SearchScreen(
        hiltViewModel(),
        onDetails,
        onNavigateUp
    )
}

@Composable
private fun SearchScreen(
    viewModel: SearchViewModel,
    onRepoClick: (Repo) -> Unit = {},
    onNavigateUp: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val items = viewModel.searchResult.collectAsLazyPagingItems()

    SearchScreen(
        state = state,
        items = items,
        onQueryChanged = { viewModel.onSearchUpdated(it) },
        onRepoClick = onRepoClick,
        onNavigateUp = onNavigateUp
    )
}

@Composable
private fun SearchScreen(
    state: SearchState,
    items: LazyPagingItems<Repo>,
    onQueryChanged: (String) -> Unit,
    onRepoClick: (Repo) -> Unit = {},
    onNavigateUp: () -> Unit = {},
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopSearchBar(
                searchText = state.query,
                placeholderText = stringResource(id = R.string.search),
                onSearchTextChanged = onQueryChanged,
                onClearClick = { onQueryChanged("") },
                onNavigateUp = onNavigateUp
            )
        }

    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Spacer(modifier = Modifier.size(8.dp))

            PaginatedRepoList(
                items = items,
                onRepoClick = onRepoClick,
                emptyState = {
                    if (state.query.isEmpty()) {
                        EmptyQuerySearchScreen(modifier = Modifier.fillParentMaxSize())
                    } else if (!state.isDebouncing) {
                        EmptyResultsSearchScreen(modifier = Modifier.fillParentMaxSize())
                    }
                }
            )
        }
    }
}
