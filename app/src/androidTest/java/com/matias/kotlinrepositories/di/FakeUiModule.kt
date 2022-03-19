package com.matias.kotlinrepositories.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Named

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [UiModule::class]
)
object FakeUiModule {

    @Provides
    @Named("debounceMs")
    @Suppress("FunctionOnlyReturningConstant")
    fun provideDebounceMilliseconds(): Long = 0L
}
