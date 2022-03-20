package com.matias.kotlinrepositories.ui.extensions

import com.matias.domain.model.NoInternetConnectionException
import com.matias.kotlinrepositories.ui.screens.ScreenStatus

fun Throwable.getScreenStatusDependingOnError(): ScreenStatus {
    return when (this) {
        is NoInternetConnectionException -> ScreenStatus.NO_INTERNET
        else -> ScreenStatus.ERROR
    }
}
