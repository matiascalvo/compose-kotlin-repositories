package com.matias.domain.repositories

import androidx.paging.PagingData
import com.github.kittinunf.result.Result
import com.matias.domain.model.Repo
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    fun getKotlinRepos(): Flow<PagingData<Repo>>
    fun searchKotlinRepos(query: String): Flow<PagingData<Repo>>
    suspend fun getKotlinRepo(owner: String, name: String): Result<Repo, Exception>
}
