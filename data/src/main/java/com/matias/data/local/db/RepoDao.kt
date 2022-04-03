package com.matias.data.local.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.matias.data.local.model.RepoDbo

@Dao
interface RepoDao {

    @Query("SELECT * FROM ${RepoDatabase.REPO_TABLE_NAME} order by stargazersCount DESC")
    fun getAll(): PagingSource<Int, RepoDbo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(items: List<RepoDbo>)

    @Query("DELETE FROM ${RepoDatabase.REPO_TABLE_NAME}")
    suspend fun deleteAll()
}
