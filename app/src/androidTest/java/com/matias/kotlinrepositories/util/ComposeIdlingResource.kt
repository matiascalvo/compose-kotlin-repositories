package com.matias.kotlinrepositories.util

import androidx.compose.ui.test.IdlingResource

class ComposeIdlingResource(
    private val idlingResource: androidx.test.espresso.IdlingResource
) : IdlingResource {

    override val isIdleNow: Boolean
        get() = idlingResource.isIdleNow
}
