package com.example.unsplashhomework.presentation.photos.list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.unsplashhomework.data.model.Photo

class PhotoDiff : DiffUtil.ItemCallback<Photo>() {

    override fun areItemsTheSame(oldItem: Photo, newItem: Photo) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo) =
        oldItem == newItem
}