package com.example.unsplashhomework.data.model

data class DigestDetails(
    val id: String,
    val title: String,
    val description: String?,
    val totalPhotos: Int,
    val tags: List<Tag>,
    val userUsername: String,
    val username: String,
    val userProfileImage: String,
    val previewPhotos: List<PreviewPhoto>
)

data class Tag(
    val title: String
)

data class PreviewPhoto(
    val id: String,
    val url: String
)
