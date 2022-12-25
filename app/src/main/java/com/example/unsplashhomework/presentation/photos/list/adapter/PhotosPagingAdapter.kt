package com.example.unsplashhomework.presentation.photos.list.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unsplashhomework.data.api.dto.PhotoDto
import com.example.unsplashhomework.databinding.PhotoViewHolderBinding

class PhotosPagingAdapter(private val onClick: (ClickableView, PhotoDto) -> Unit) :
    PagingDataAdapter<PhotoDto, PhotoViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        Log.d("Kart", "onCreateViewHolder: ")
        val binding =
            PhotoViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        Log.d("Kart", "onBindViewHolder: ")
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                Glide
                    .with(photo.context)
                    .load(it.urls.small)
                    .into(photo)
            }
            item?.let {
                Glide
                    .with(authorAvatar.context)
                    .load(it.user.profileImage.small)
                    .into(authorAvatar)
            }
            authorName.text = item?.user?.name
            currentLikes.text = item?.likes.toString()
            isLiked.isSelected = item?.likedByUser == true //пока что обновляется только при перезагрузке страницы
            Log.d("Kart", "viewlike = ${isLiked.isSelected}   serverLike = ${item?.likedByUser}")

            holder.binding.photo.setOnClickListener {
                item?.let {
                    onClick(ClickableView.PHOTO, item)
                }
            }
            holder.binding.isLiked.setOnClickListener {
                item?.let {
                    onClick(ClickableView.LIKE, item)
                }
            }
        }
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<PhotoDto>() {
    override fun areItemsTheSame(
        oldItem: PhotoDto,
        newItem: PhotoDto
    ): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: PhotoDto,
        newItem: PhotoDto
    ): Boolean =
        (oldItem == newItem)
}

class PhotoViewHolder(val binding: PhotoViewHolderBinding) :
    RecyclerView.ViewHolder(binding.root)
