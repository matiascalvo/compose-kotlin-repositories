package com.matias.data.remote.model

import com.google.gson.annotations.SerializedName

data class ResultListDto<T>(
    @SerializedName("total_count")
    val totalCount: Int = 0,
    @SerializedName("items")
    val items: List<T> = emptyList()
)
