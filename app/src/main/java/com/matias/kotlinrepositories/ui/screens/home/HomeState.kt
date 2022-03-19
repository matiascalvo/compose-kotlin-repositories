package com.matias.kotlinrepositories.ui.screens.home

import com.matias.domain.model.Repo
import com.matias.kotlinrepositories.ui.screens.ScreenStatus

data class HomeState(
    val searchQuery: String = "",
    val list: List<Repo> = emptyList(),
    val isLoading: Boolean = true,
    val screenStatus: ScreenStatus = ScreenStatus.IDLE,
    val showEmptyState: Boolean = false,
)
