package com.example.unsplashhomework.data.remote.photosmodel

data class Sponsor(
    val acceptedTos: Boolean,
    val bio: String,
    val firstName: String,
    val forHire: Boolean,
    val id: String,
    val instagramUsername: String,
    val lastName: Any?,
    val links: LinksX,
    val location: Any?,
    val name: String,
    val portfolioUrl: String,
    val profileImage: ProfileImage,
    val social: Social,
    val totalCollections: Int,
    val totalLikes: Int,
    val totalPhotos: Int,
    val twitterUsername: String,
    val updatedAt: String,
    val username: String
)