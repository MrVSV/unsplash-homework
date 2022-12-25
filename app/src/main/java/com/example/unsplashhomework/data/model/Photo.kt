package com.example.unsplashhomework.data.model

data class Photo(
    val id: String,
    val urlsSmall: String,
    val likedByUser: Boolean,
    val likes: Int,
    val userName: String,
    val userAvatar: String,
    val height: Int,
    val width: Int
)
