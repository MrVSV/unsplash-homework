package com.example.unsplashhomework.presentation.collections.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashhomework.R
import com.example.unsplashhomework.domain.model.Digest
import com.example.unsplashhomework.databinding.DigestViewHolderBinding
import com.example.unsplashhomework.tools.loadImage

class DigestViewHolder(private val binding: DigestViewHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Digest, onClick: (item: Digest) -> Unit) {

        binding.root.setOnClickListener {
            onClick(item)
        }

        binding.preview.loadImage(item.previewPhoto)
        binding.authorAvatar.loadImage(item.userProfileImage)
        binding.authorName.text = item.userUsername
        binding.totalPhotos.text =
            itemView.context.resources.getQuantityString(
                R.plurals.total_photos,
                item.totalPhotos,
                item.totalPhotos
            )
        binding.collectionTitle.text = item.title

    }
}

