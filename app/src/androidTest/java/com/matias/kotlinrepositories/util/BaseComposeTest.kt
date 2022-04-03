package com.matias.kotlinrepositories.util

import androidx.annotation.StringRes
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.matias.kotlinrepositories.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.After
import org.junit.Before
import org.junit.Rule

abstract class BaseComposeTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    open fun setUp() {
        hiltRule.inject()
    }

    @After
    open fun tearDown() {
    }

    protected fun getString(@StringRes res: Int) = composeTestRule.activity.getString(res)
}
