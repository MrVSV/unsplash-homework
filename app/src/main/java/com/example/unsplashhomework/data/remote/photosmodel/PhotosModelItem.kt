package com.example.unsplashhomework.data.remote.photosmodel

data class PhotosModelItem(
    val altDescription: String?,
    val blurHash: String,
    val color: String,
    val createdAt: String,
    val currentUserCollections: List<Any>,
    val description: String?,
    val height: Int,
    val id: String,
    val likedByUser: Boolean,
    val likes: Int,
    val links: Links,
    val promotedAt: String?,
    val sponsorship: Sponsorship?,
    val topicSubmissions: TopicSubmissions?,
    val updatedAt: String,
    val urls: Urls,
    val user: User,
    val width: Int
)