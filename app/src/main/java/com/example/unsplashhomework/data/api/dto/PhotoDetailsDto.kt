package com.example.unsplashhomework.data.api.dto

data class PhotoDetailsDto(
    val id: String,
    val downloads: Int,
    val likes: Int,
    val likedByUser: Boolean,
    val exif: ExifDto,
    val location: LocationDto,
    val tags: List<TagDto>,
    val urls: UrlsDto,
    val links: LinksDto,
    val user: UserDto
)