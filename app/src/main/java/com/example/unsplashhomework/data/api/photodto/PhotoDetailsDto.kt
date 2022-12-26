package com.example.unsplashhomework.data.api.photodto

import com.google.gson.annotations.SerializedName

data class PhotoDetailsDto(
    val id: String,
    val downloads: Int,
    val likes: Int,
    @SerializedName("liked_by_user")
    val likedByUser: Boolean,
    val exif: ExifDto,
    val location: LocationDto,
    val tags: List<TagDto>,
    val urls: UrlsDto,
    val links: LinksDto,
    val user: UserDto
)