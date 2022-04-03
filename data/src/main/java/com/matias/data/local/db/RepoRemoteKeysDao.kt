package com.matias.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.matias.data.local.model.RepoRemoteKeysDbo

@Dao
interface RepoRemoteKeysDao {

    @Query("SELECT * FROM ${RepoDatabase.REPO_REMOTE_KEYS_TABLE_NAME} WHERE id =:id")
    suspend fun get(id: Int): RepoRemoteKeysDbo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(remoteKeys: List<RepoRemoteKeysDbo>)

    @Query("DELETE FROM ${RepoDatabase.REPO_REMOTE_KEYS_TABLE_NAME}")
    suspend fun deleteAll()
}
