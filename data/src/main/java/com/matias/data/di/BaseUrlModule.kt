package com.matias.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@InstallIn(SingletonComponent::class)
@Module
object BaseUrlModule {

    @Provides
    @Named("github-base-url")
    @Suppress("FunctionOnlyReturningConstant")
    fun provideBaseUrl(): String = "https://api.github.com/"
}
