package com.matias.kotlinrepositories.di

import android.content.Context
import androidx.room.Room
import com.matias.data.di.DatabaseModule
import com.matias.data.local.db.RepoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [DatabaseModule::class])
object InMemoryDatabaseModule {

    @Singleton
    @Provides
    fun provideInMemoryDbEmpty(@ApplicationContext context: Context): RepoDatabase {
        return Room.inMemoryDatabaseBuilder(context, RepoDatabase::class.java).build()
    }
}
