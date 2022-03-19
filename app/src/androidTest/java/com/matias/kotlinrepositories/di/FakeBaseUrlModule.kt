package com.matias.kotlinrepositories.di

import com.matias.data.di.BaseUrlModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Named

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [BaseUrlModule::class]
)
object FakeBaseUrlModule {

    @Provides
    @Named("github-base-url")
    @Suppress("FunctionOnlyReturningConstant")
    fun provideBaseUrl(): String = "http://localhost:8080/"
}
