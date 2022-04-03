package com.matias.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.matias.data.local.model.RepoDbo
import com.matias.data.local.model.RepoRemoteKeysDbo

@Database(entities = [RepoDbo::class, RepoRemoteKeysDbo::class], version = 1, exportSchema = false)
abstract class RepoDatabase : RoomDatabase() {

    abstract fun repoDao(): RepoDao
    abstract fun repoRemoteKeysDao(): RepoRemoteKeysDao

    companion object {
        const val DATABASE_NAME = "database_name"
        const val REPO_TABLE_NAME = "repo_table"
        const val REPO_REMOTE_KEYS_TABLE_NAME = "repo_remote_keys_table"
    }
}
