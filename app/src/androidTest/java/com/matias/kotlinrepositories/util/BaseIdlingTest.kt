package com.matias.kotlinrepositories.util
    .junit.Rule

import com.matias.core.utils.EspressoIdlingResource
import com.matias.kotlinrepositories.util.BaseComposeTest
import com.matias.kotlinrepositories.util.ComposeIdlingResource
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
