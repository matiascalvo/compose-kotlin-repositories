package com.matias.kotlinrepositories.ui.screens.search

data class SearchState(
    val query: String = "",
    val isDebouncing: Boolean = false,
)
