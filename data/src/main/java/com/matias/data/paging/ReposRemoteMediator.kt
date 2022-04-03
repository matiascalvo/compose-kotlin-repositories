package com.matias.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.matias.core.utils.EspressoIdlingResource
import com.matias.data.local.db.RepoDao
import com.matias.data.local.db.RepoDatabase
import com.matias.data.local.mappers.RepoDboMapper
import com.matias.data.local.model.RepoDbo
import com.matias.data.local.model.RepoRemoteKeysDbo
import com.matias.data.remote.datasource.GithubDataSource

@ExperimentalPagingApi
class ReposRemoteMediator(
    private val dataSource: GithubDataSource,
    private val database: RepoDatabase,
    private val mapper: RepoDboMapper,
) : RemoteMediator<Int, RepoDbo>() {

    private val repoDao: RepoDao = database.repoDao()
    private val repoRemoteKeysDao = database.repoRemoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepoDbo>
    ): MediatorResult {
        return EspressoIdlingResource.wrap { wrappedLoad(loadType, state) }
    }

    @Suppress("ReturnCount", "TooGenericExceptionCaught")
    private suspend fun wrappedLoad(
        loadType: LoadType,
        state: PagingState<Int, RepoDbo>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = dataSource.searchRepositories(page = currentPage)
            val endOfPaginationReached = dataSource.isLastPage(currentPage, response.totalCount)

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    repoDao.deleteAll()
                    repoRemoteKeysDao.deleteAll()
                }
                val keys = response.items.map { item ->
                    RepoRemoteKeysDbo(
                        id = item.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                repoRemoteKeysDao.addAll(remoteKeys = keys)
                repoDao.add(items = response.items.map { mapper.mapFromDomainModel(it) })
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, RepoDbo>
    ): RepoRemoteKeysDbo? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                repoRemoteKeysDao.get(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, RepoDbo>
    ): RepoRemoteKeysDbo? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { item ->
                repoRemoteKeysDao.get(id = item.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, RepoDbo>
    ): RepoRemoteKeysDbo? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { item ->
                repoRemoteKeysDao.get(id = item.id)
            }
    }
}
