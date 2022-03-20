package com.matias.kotlinrepositories.ui.screens.details

import com.matias.domain.model.Repo
import com.matias.kotlinrepositories.ui.screens.ScreenStatus

data class DetailsScreenState(
    val repo: Repo? = null,
    val screenStatus: ScreenStatus = ScreenStatus.LOADING
) {
    companion object {
        val Empty = DetailsScreenState(repo = null, screenStatus = ScreenStatus.LOADING)
    }
}
