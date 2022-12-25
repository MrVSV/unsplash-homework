package com.example.unsplashhomework.data.api.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    val bio: String?,
    val name: String,
    @SerializedName("profile_image")
    val profileImage: ProfileImageDto,
    val username: String
)