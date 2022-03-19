package com.matias.data.remote.services

import com.matias.data.remote.model.RepoDto
import com.matias.data.remote.model.ResultList
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchServiceApi {

    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("s") sortBy: String,
    ): ResultList<RepoDto>
}
