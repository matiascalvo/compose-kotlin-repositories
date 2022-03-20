package com.matias.kotlinrepositories.ui.navigation

import com.matias.domain.model.Repo

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Details : Screen("repo/{owner}/{name}") {
        fun createRoute(repo: Repo) = "repo/${repo.owner.login}/${repo.name}"
    }
}
