package com.example.unsplashhomework.presentation.collections.adapter

import androidx.recyclerview.widget.RecyclerView
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
        binding.totalPhotos.text = "${item.totalPhotos} photos"
        binding.collectionTitle.text = item.title

    }
}

