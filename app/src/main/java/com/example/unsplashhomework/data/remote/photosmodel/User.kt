package com.example.unsplashhomework.data.remote.photosmodel

import com.google.gson.annotations.SerializedName

data class User(
    val acceptedTos: Boolean,
    val bio: String?,
    val firstName: String,
    val forHire: Boolean,
    val id: String,
    val instagram_username: String?,
    val lastName: String?,
    val links: LinksX,
    val location: String?,
    val name: String,
    val portfolioUrl: String?,
    @SerializedName("profile_image")
    val profileImage: ProfileImage,
    val social: SocialX,
    val totalCollections: Int,
    val totalLikes: Int,
    val totalPhotos: Int,
    val twitterUsername: String?,
    val updatedAt: String,
    val username: String
)