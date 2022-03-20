package com.matias.domain.repositories

import com.github.kittinunf.result.Result
import com.matias.domain.model.Repo

interface GithubRepository {
    suspend fun getKotlinRepos(query: String, page: Int = 1): Result<List<Repo>, Exception>
    suspend fun getKotlinRepo(fullName: String): Result<Repo, Exception>
}
