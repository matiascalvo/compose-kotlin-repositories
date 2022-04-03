package com.matias.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.matias.data.local.db.RepoDatabase

@Entity(tableName = RepoDatabase.REPO_REMOTE_KEYS_TABLE_NAME)
data class RepoRemoteKeysDbo(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?
)
