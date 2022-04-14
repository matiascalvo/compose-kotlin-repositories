package com.matias.kotlinrepositories.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.matias.domain.model.Repo
import com.matias.kotlinrepositories.R
import com.matias.kotlinrepositories.ui.composables.EmptyQuerySearchScreen
import com.matias.kotlinrepositories.ui.composables.PaginatedRepoList

const val SCROLL_TO_TOP_TEST_TAG = "scroll_to_top_test_tag"

@Composable
fun HomeScreen(
    onDetails: (Repo) -> Unit = {},
    onSearch: () -> Unit = {},
) {
    HomeScreen(hiltViewModel(), onDetails, onSearch)
}

@Composable
private fun HomeScreen(
    viewModel: HomeViewModel,
    onRepoClick: (Repo) -> Unit = {},
    onSearch: () -> Unit = {}
) {
    val items = viewModel.items.collectAsLazyPagingItems()

    HomeScreenContent(
        items = items,
        onRepoClick = onRepoClick,
        onSearch = onSearch,
    )
}

@Composable
private fun HomeScreenContent(
    items: LazyPagingItems<Repo>,
    onRepoClick: (Repo) -> Unit = {},
    onSearch: () -> Unit = {},
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                actions = {
                    IconButton(onClick = onSearch) {
                        Icon(Icons.Default.Search, contentDescription = stringResource(id = R.string.search))
                    }
                }

            )
        }

    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Spacer(modifier = Modifier.size(8.dp))

            PaginatedRepoList(
                items = items,
                onRepoClick = onRepoClick,
                emptyState = { EmptyQuerySearchScreen(modifier = Modifier.fillParentMaxSize()) }
            )
        }
    }
}
