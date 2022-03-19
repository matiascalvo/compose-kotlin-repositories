package com.matias.data.remote.model

import com.google.gson.annotations.SerializedName

data class RepoDto(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("full_name")
    val fullName: String = "",
    @SerializedName("owner")
    val owner: UserDto = UserDto(),
    @SerializedName("html_url")
    val htmlUrl: String? = "",
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("homepage")
    val homepage: String? = "",
    @SerializedName("stargazers_count")
    val stargazersCount: Int = 0,
    @SerializedName("language")
    val language: String? = "",
    @SerializedName("forks_count")
    val forksCount: Int = 0,
)
