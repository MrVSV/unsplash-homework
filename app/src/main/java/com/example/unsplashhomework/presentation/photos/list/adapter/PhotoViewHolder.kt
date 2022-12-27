package com.example.unsplashhomework.presentation.photos.list.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashhomework.data.model.Photo
import com.example.unsplashhomework.data.state.ClickableView
import com.example.unsplashhomework.databinding.PhotoViewHolderBinding
import com.example.unsplashhomework.tools.loadImage

class PhotoViewHolder(private val binding: PhotoViewHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Photo, onClick: (ClickableView, item: Photo) -> Unit) {


        binding.photo.setOnClickListener {
            onClick(ClickableView.PHOTO, item)
        }
        binding.isLiked.setOnClickListener {
//            binding.isLiked.isSelected =! binding.isLiked.isSelected
            onClick(ClickableView.LIKE, item)
        }
//        binding.progressBar.isVisible = item.isLikeProgress
        binding.currentLikes.text = item.likes.toString()
        binding.isLiked.isSelected = item.likedByUser

        binding.photo.loadImage(item.urlsSmall)
        binding.authorAvatar.loadImage(item.userAvatar)

        binding.authorName.text = item.userName
    }
}

