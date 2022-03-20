package com.matias.data.repositories

import com.github.kittinunf.result.Result
import com.github.kittinunf.result.map
import com.github.kittinunf.result.onSuccess
import com.matias.data.remote.datasource.GithubDataSource
import com.matias.data.remote.model.mappers.RepoDtoMapper
import com.matias.domain.model.NoInternetConnectionException
import com.matias.domain.model.Repo
import com.matias.domain.repositories.GithubRepository
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val githubDatasource: GithubDataSource,
    private val mapper: RepoDtoMapper,
    private val cache: RepositoryCache,
) : GithubRepository {

    @Suppress("TooGenericExceptionCaught")
    override suspend fun getKotlinRepos(query: String, page: Int): Result<List<Repo>, Exception> {
        return try {
            val result = githubDatasource.searchRepositories(
                query = query.trim(),
                page = page,
            ).items
            Result.success(result)
        } catch (e: Exception) {
            when (e) {
                is ConnectException, is SocketTimeoutException, is UnknownHostException ->
                    Result.failure(NoInternetConnectionException())
                else -> Result.failure(e)
            }
        }.map { list -> list.map { mapper.mapToDomainModel(it) } }
            .onSuccess { list -> cache.saveRepos(list) }
    }

    override suspend fun getKotlinRepo(fullName: String): Result<Repo, Exception> {
        return Result.of {
            val cachedValue = cache.getRepo(fullName)
            if (cachedValue != null) {
                cachedValue
            } else {
                val repo = githubDatasource.getRepository(fullName)
                mapper.mapToDomainModel(repo)
            }
        }
    }
}
