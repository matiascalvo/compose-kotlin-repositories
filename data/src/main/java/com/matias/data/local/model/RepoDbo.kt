package com.matias.data.local.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.matias.data.local.db.RepoDatabase

@Entity(tableName = RepoDatabase.REPO_TABLE_NAME)
data class RepoDbo(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val name: String = "",
    val fullName: String = "",
    @Embedded
    val owner: UserDbo = UserDbo(),
    val htmlUrl: String? = "",
    val description: String? = "",
    val homepage: String? = "",
    val stargazersCount: Int = 0,
    val language: String? = "",
    val forksCount: Int = 0,
)
