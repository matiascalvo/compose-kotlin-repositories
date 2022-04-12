package com.matias.domain.usecases

import com.matias.domain.repositories.GithubRepository
import javax.inject.Inject

class GetRepoUseCase @Inject constructor(
    private val repository: GithubRepository,
) {
    suspend operator fun invoke(owner: String, name: String) =
        repository.getKotlinRepo(owner, name)
}
