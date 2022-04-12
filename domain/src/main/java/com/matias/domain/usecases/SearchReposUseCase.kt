package com.matias.domain.usecases

import com.matias.domain.repositories.GithubRepository
import javax.inject.Inject

class SearchReposUseCase @Inject constructor(
    private val repo: GithubRepository
) {

    operator fun invoke(query: String) = repo.searchKotlinRepos(query)
}
