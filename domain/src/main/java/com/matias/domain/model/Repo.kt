package com.matias.domain.model

data class Repo(
    val id: Int = 0,
    val name: String = "",
    val fullName: String = "",
    val owner: User = User(),
    val description: String = "",
    val url: String = "",
    val homepage: String = "",
    val stars: Int = 0,
    val language: String = "",
    val forks: Int = 0,
)
