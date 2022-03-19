package com.matias.kotlinrepositories.ui.screens.home

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.matias.domain.model.Repo
import com.matias.domain.model.fakeRepo1
import com.matias.kotlinrepositories.R
import com.matias.kotlinrepositories.ui.composables.CustomCircularProgressBar
import com.matias.kotlinrepositories.ui.composables.EmptySearchScreen
import com.matias.kotlinrepositories.ui.composables.NetworkErrorScreen
import com.matias.kotlinrepositories.ui.composables.RepoListItem
import com.matias.kotlinrepositories.ui.composables.TopSearchBar
import com.matias.kotlinrepositories.ui.composables.UnknownErrorScreen
import com.matias.kotlinrepositories.ui.screens.ScreenStatus
import com.matias.kotlinrepositories.ui.theme.KotlinRepositoriesTheme
import kotlinx.coroutines.launch

const val HOME_LIST_TEST_TAG = "home_list_test_tag"
const val SCROLL_TO_TOP_TEST_TAG = "scroll_to_top_test_tag"

@Composable
fun HomeScreen(onDetails: (Repo) -> Unit = {}) {
    HomeScreen(hiltViewModel(), onDetails)
}

@Composable
private fun HomeScreen(viewModel: HomeViewModel, onRepoClick: (Repo) -> Unit = {}) {
    val state by viewModel.state.collectAsState(initial = HomeState())

    HomeScreen(
        state = state,
        onQueryChanged = { viewModel.onSearchUpdated(it) },
        onReachedLastElement = { viewModel.onReachedLastElement() },
        onRepoClick = onRepoClick
    )
}

@Composable
private fun HomeScreen(
    state: HomeState,
    onQueryChanged: (String) -> Unit,
    onReachedLastElement: () -> Unit = {},
    onRepoClick: (Repo) -> Unit = {}
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopSearchBar(
                searchText = state.searchQuery,
                placeholderText = stringResource(id = R.string.search),
                onSearchTextChanged = onQueryChanged
            ) { onQueryChanged("") }
        }

    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Spacer(modifier = Modifier.size(8.dp))

            HomeContent(state, onReachedLastElement, onRepoClick)
        }
        CustomCircularProgressBar(state.screenStatus.isLoading())
    }
}

@Composable
private fun HomeContent(
    state: HomeState,
    onReachedLastElement: () -> Unit,
    onRepoClick: (Repo) -> Unit
) {
    when {
        state.screenStatus == ScreenStatus.NO_INTERNET -> NetworkErrorScreen()
        state.screenStatus == ScreenStatus.ERROR -> UnknownErrorScreen()
        state.showEmptyState -> EmptySearchScreen()
        else -> HomeListContent(
            state.list,
            onReachedLastElement = onReachedLastElement,
            onClick = onRepoClick
        )
    }
}

@Composable
private fun HomeListContent(
    elements: List<Repo>,
    onReachedLastElement: () -> Unit = {},
    onClick: (Repo) -> Unit = {},
) {
    val listState = rememberLazyListState()
    val showButton by remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .testTag(HOME_LIST_TEST_TAG)
        ) {
            itemsIndexed(elements) { index, item ->
                if (isLastElement(elements, index, listState)) {
                    onReachedLastElement()
                }
                RepoListItem(repo = item, onClick = onClick)
            }
        }
        AnimatedVisibility(visible = showButton, enter = fadeIn(), exit = fadeOut()) {
            ScrollToTopButton(listState)
        }
    }
}

private fun isLastElement(
    elements: List<Repo>,
    index: Int,
    listState: LazyListState
) = (
    elements.size == index + 1 &&
        listState.layoutInfo.visibleItemsInfo.isNotEmpty() &&
        listState.layoutInfo.visibleItemsInfo.size < elements.size
    )

@Composable
private fun ScrollToTopButton(listState: LazyListState) {
    val coroutineScope = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            onClick = { coroutineScope.launch { listState.animateScrollToItem(0) } },
            modifier = Modifier.Companion
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .testTag(SCROLL_TO_TOP_TEST_TAG)
        ) {
            Icon(Icons.Default.ArrowUpward, contentDescription = null)
        }
    }
}

@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Day Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
@Suppress("MagicNumber")
private fun Preview() {
    KotlinRepositoriesTheme() {
        HomeContent(state = HomeState(list = List(10) { fakeRepo1 }), {}, {})
    }
}
