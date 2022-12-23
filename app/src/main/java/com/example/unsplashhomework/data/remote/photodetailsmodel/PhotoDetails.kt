package com.example.unsplashhomework.data.remote.photodetailsmodel

import com.example.unsplashhomework.data.remote.photosmodel.Links
import com.example.unsplashhomework.data.remote.photosmodel.Urls
import com.example.unsplashhomework.data.remote.photosmodel.User

data class PhotoDetails(
    val id: String,
    val created_at: String,
    val updated_at: String,
    val width: Int,
    val height: Int,
    val color: String,
    val blur_hash: String,
    val downloads: Int,
    val likes: Int,
    val liked_by_user: Boolean,
    val public_domain: Boolean,
    val description: String?,
    val exif: Exif,
    val location: Location,
    val tags: List<Tag>,
   //val current_user_collections: List<UserCollection>,
    val urls: Urls,
    val links: Links,
    val user: User
)