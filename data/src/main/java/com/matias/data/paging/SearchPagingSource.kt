package com.matias.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.matias.core.utils.EspressoIdlingResource
import com.matias.data.remote.RemoteConstants
import com.matias.data.remote.datasource.GithubDataSource
import com.matias.domain.model.Repo

class SearchPagingSource(
    private val dataSource: GithubDataSource,
    private val query: String
) : PagingSource<Int, Repo>() {

    @Suppress("TooGenericExceptionCaught")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        return EspressoIdlingResource.wrap { wrappedLoad(params) }
    }

    @Suppress("TooGenericExceptionCaught")
    private suspend fun wrappedLoad(params: LoadParams<Int>): LoadResult<Int, Repo> {
        val currentPage = params.key ?: RemoteConstants.FIRST_PAGE
        return try {
            if (query.isEmpty()) {
                emptyPage()
            } else {
                val response = dataSource.searchRepositories(currentPage, query = query)
                if (response.items.isNotEmpty()) {
                    LoadResult.Page(
                        data = response.items,
                        prevKey = if (currentPage == RemoteConstants.FIRST_PAGE) null else currentPage - 1,
                        nextKey = if (dataSource.isLastPage(currentPage, response.totalCount)) null else currentPage + 1
                    )
                } else {
                    emptyPage()
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun emptyPage(): LoadResult.Page<Int, Repo> =
        LoadResult.Page(
            data = emptyList(),
            prevKey = null,
            nextKey = null
        )

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        return state.anchorPosition
    }
}
