package com.matias.data.remote.datasource

import com.matias.data.Constants
import com.matias.data.remote.model.mappers.RepoDtoMapper
import com.matias.data.remote.model.mappers.RepoResultListDtoMapper
import com.matias.data.remote.services.SearchServiceApi
import com.matias.domain.model.NoInternetConnectionException
import com.matias.domain.model.Repo
import com.matias.domain.model.ResultList
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class GithubDataSource @Inject constructor(
    private val service: SearchServiceApi,
    private val repoDtoMapper: RepoDtoMapper,
    private val resultDtoMapper: RepoResultListDtoMapper
) {

    suspend fun searchRepositories(
        page: Int,
        query: String = "",
    ): ResultList<Repo> {
        return call {
            val result = service.searchRepositories(query = getKotlinQuery(query), page = page, sortBy = "stars")
            resultDtoMapper.mapToDomainModel(result)
        }
    }

    suspend fun getRepository(owner: String, name: String) =
        call { repoDtoMapper.mapToDomainModel(service.getRepository(owner, name)) }

    fun isLastPage(currentPage: Int, totalCount: Int) =
        Constants.ELEMENTS_PER_PAGE * (currentPage + 1) > totalCount

    @Suppress("TooGenericExceptionCaught")
    private suspend fun <T> call(call: suspend () -> T): T {
        return try {
            call.invoke()
        } catch (e: Exception) {
            when (e) {
                is ConnectException, is SocketTimeoutException, is UnknownHostException ->
                    throw NoInternetConnectionException()
                else -> throw e
            }
        }
    }

    private fun getKotlinQuery(query: String): String {
        return "$query${if (query.isNotEmpty()) "+" else ""}language:kotlin"
    }
}
