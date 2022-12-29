package com.example.unsplashhomework.data.api.photodto

import com.example.unsplashhomework.domain.model.User
import com.google.gson.annotations.SerializedName

data class UserDto(
    val bio: String?,
    val name: String,
    @SerializedName("profile_image")
    val profileImage: ProfileImageDto,
    val username: String
) {
    fun toPhotoDetailsUser() = User(bio, name, profileImage.toPhotoDetailsProfileImage(), username)
}