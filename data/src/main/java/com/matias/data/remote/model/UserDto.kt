package com.matias.data.remote.model

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("login")
    val login: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("avatar_url")
    val avatarUrl: String = "",
)
