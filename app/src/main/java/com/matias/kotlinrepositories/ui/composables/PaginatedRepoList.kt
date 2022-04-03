package com.matias.kotlinrepositories.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.matias.domain.model.Repo
import com.matias.kotlinrepositories.R
import com.matias.kotlinrepositories.ui.composables.paging.ErrorItem
import com.matias.kotlinrepositories.ui.composables.paging.LoadingItem
import kotlinx.coroutines.launch

const val REPO_LIST = "REPO_LIST"
const val SCROLL_TO_TOP_TEST_TAG = "scroll_to_top_test_tag"

@Composable
fun PaginatedRepoList(
    items: LazyPagingItems<Repo>,
    onRepoClick: (Repo) -> Unit,
    emptyState: @Composable LazyItemScope.() -> Unit
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
                .testTag(REPO_LIST)
        ) {
            items(items) { item ->
                item?.let {
                    RepoListItem(repo = item, onClick = onRepoClick)
                }
            }

            items.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { LoadingScreen(modifier = Modifier.fillParentMaxSize()) }
                    }
                    loadState.append is LoadState.Loading -> {
                        item { LoadingItem() }
                    }
                    loadState.refresh is LoadState.Error -> {
                        item {
                            UndefinedErrorScreen(
                                e = (loadState.refresh as LoadState.Error).error,
                                onClickRetry = { retry() }
                            )
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        item {
                            ErrorItem(
                                stringResource(id = R.string.unknown_error),
                                onClickRetry = { retry() }
                            )
                        }
                    }
                    loadState.source.refresh is LoadState.NotLoading &&
                        loadState.append.endOfPaginationReached &&
                        items.itemCount < 1 -> {
                        item(content = emptyState)
                    }
                }
            }
        }
        AnimatedVisibility(visible = showButton, enter = fadeIn(), exit = fadeOut()) {
            ScrollToTopButton(listState)
        }
    }
}

@Composable
private fun ScrollToTopButton(listState: LazyListState) {
    val coroutineScope = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            onClick = { coroutineScope.launch { listState.animateScrollToItem(0) } },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .testTag(SCROLL_TO_TOP_TEST_TAG)
        ) {
            Icon(Icons.Default.ArrowUpward, contentDescription = null)
        }
    }
}
