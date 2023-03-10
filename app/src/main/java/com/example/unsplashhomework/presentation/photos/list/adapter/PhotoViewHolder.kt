package com.example.unsplashhomework.presentation.photos.list.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashhomework.data.state.ClickableView
import com.example.unsplashhomework.databinding.PhotoViewHolderBinding
import com.example.unsplashhomework.domain.model.Photo
import com.example.unsplashhomework.tools.loadImage

class PhotoViewHolder(private val binding: PhotoViewHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Photo, onClick: (ClickableView, item: Photo) -> Unit) {


        binding.photo.setOnClickListener {
            onClick(ClickableView.PHOTO, item)
        }
        binding.isLiked.setOnClickListener {
            onClick(ClickableView.LIKE, item)
        }

        binding.currentLikes.text = item.likes.toString()
        binding.isLiked.isSelected = item.likedByUser

        binding.photo.loadImage(item.urlsSmall)
        binding.authorAvatar.loadImage(item.userAvatar)

        binding.authorName.text = item.userName


    }
}

