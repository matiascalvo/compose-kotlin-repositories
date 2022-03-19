package com.matias.data.di

import com.matias.data.repositories.GithubRepositoryImpl
import com.matias.domain.repositories.GithubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("UnnecessaryAbstractClass")
@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideGithubRepository(bind: GithubRepositoryImpl): GithubRepository
}
