package com.matias.domain.usecases

import com.matias.domain.repositories.GithubRepository
import javax.inject.Inject

class GetPagedKotlinReposUseCase @Inject constructor(
    private val repo: GithubRepository
) {
    operator fun invoke() = repo.getKotlinRepos()
}
