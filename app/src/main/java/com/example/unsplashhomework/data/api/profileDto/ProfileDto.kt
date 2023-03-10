package com.example.unsplashhomework.data.api.profileDto


import com.example.unsplashhomework.domain.model.Profile
import com.google.gson.annotations.SerializedName

data class ProfileDto(
    val id: String,
    val username: String,
    val name: String?,
    val location: String?,
    @SerializedName("profile_image")
    val profileImage: ProfileImageDto,
    @SerializedName("total_likes")
    val totalLikes: Int
) {
    fun toProfile() = Profile(
        id = id,
        userName = username,
        name = name,
        location = location,
        avatar = profileImage.large,
        totalLikes = totalLikes
    )
}