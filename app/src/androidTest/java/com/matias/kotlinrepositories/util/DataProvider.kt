package com.matias.kotlinrepositories.util

import com.matias.domain.model.Repo
import com.matias.domain.model.User

object DataProvider {

    fun provideRepo(
        id: Int = 1,
        name: String = "okhttp",
        fullName: String = "square/okhttp",
        owner: User = User(
            login = "square",
            avatarUrl = "https://avatars.githubusercontent.com/u/82592?v=4"
        ),
        description: String = "Square’s meticulous HTTP client for the JVM, Android, and GraalVM.",
        url: String = "https://github.com/square/okhttp",
        homepage: String = "https://square.github.io/okhttp/",
        stars: Int = 45001,
        language: String = "Kotlin",
        forks: Int = 8793,
    ) = Repo(
        id = id,
        name = name,
        fullName = fullName,
        owner = owner,
        description = description,
        url = url,
        homepage = homepage,
        stars = stars,
        language = language,
        forks = forks,
    )
}
