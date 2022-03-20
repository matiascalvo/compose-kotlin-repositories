package com.matias.data.remote.datasource

import com.matias.data.remote.services.SearchServiceApi
import javax.inject.Inject

class GithubDataSource @Inject constructor(
    private val service: SearchServiceApi,
) {

    suspend fun searchRepositories(
        query: String,
        page: Int,
    ) = service.searchRepositories(query = getKotlinQuery(query), page = page, sortBy = "stars")

    fun getRepository(fullName: String) = service.getRepository(fullName)

    private fun getKotlinQuery(query: String): String {
        return "$query${if (query.isNotEmpty()) "+" else ""}language:kotlin"
    }
}
