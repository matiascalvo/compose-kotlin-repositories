package com.matias.data.remote

import androidx.collection.LruCache
import com.matias.domain.model.Repo
import javax.inject.Inject

private const val MAX_CACHED_REPOS = 200

class RepositoryCache @Inject constructor() {

    private val cache = LruCache<String, Repo>(MAX_CACHED_REPOS)

    fun getRepo(value: String) = cache[value]

    fun saveRepos(repos: List<Repo>) {
        repos.forEach { cache.put(it.fullName, it) }
    }
}
