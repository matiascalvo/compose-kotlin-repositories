package com.matias.data.di

import android.content.Context
import androidx.room.Room
import com.matias.data.local.db.RepoDao
import com.matias.data.local.db.RepoDatabase
import com.matias.data.local.db.RepoRemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): RepoDatabase {
        return Room.databaseBuilder(
            context,
            RepoDatabase::class.java,
            RepoDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepoDao(db: RepoDatabase): RepoDao = db.repoDao()

    @Provides
    @Singleton
    fun provideRemoteKeysDao(db: RepoDatabase): RepoRemoteKeysDao = db.repoRemoteKeysDao()
}
