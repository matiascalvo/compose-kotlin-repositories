package com.matias.kotlinrepositories.util

import com.matias.core.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before

abstract class BaseIdlingTest : BaseComposeTest() {

    private val composeIdlingResource = ComposeIdlingResource(EspressoIdlingResource.countingIdlingResource)

    @Before
    override fun setUp() {
        composeTestRule.registerIdlingResource(composeIdlingResource)
    }

    @After
    override fun tearDown() {
        composeTestRule.unregisterIdlingResource(composeIdlingResource)
    }
}
