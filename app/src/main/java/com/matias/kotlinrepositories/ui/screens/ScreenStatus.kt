package com.matias.kotlinrepositories.ui.screens

enum class ScreenStatus {
    IDLE, LOADING, NO_INTERNET, ERROR;

    fun isLoading(): Boolean = this == LOADING
}
