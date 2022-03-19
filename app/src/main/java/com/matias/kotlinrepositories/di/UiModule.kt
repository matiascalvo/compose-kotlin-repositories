package com.matias.kotlinrepositories.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@InstallIn(SingletonComponent::class)
@Module
object UiModule {

    @Provides
    @Named("debounceMs")
    @Suppress("FunctionOnlyReturningConstant")
    fun provideDebounceMilliseconds(): Long = 600L
}
