package com.example.unsplashhomework.tools

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.unsplashhomework.R
import com.example.unsplashhomework.data.api.photodto.PhotoDto
import com.example.unsplashhomework.data.model.Photo

fun List<PhotoDto>.toListPhoto(): List<Photo> {
    val newList = mutableListOf<Photo>()

    this.forEach{ item->
        newList.add(item.toPhoto())
    }
    return newList
}

fun ImageView.loadImage(urls:String){
    Glide.with(this)
        .load(urls)
        .error(R.drawable.error_image)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(R.drawable.placeholder)
        .into(this)
}

fun ImageView.loadImage(imageId:Int){
    Glide.with(this)
        .load(imageId)
        .error(R.drawable.error_image)
        .placeholder(R.drawable.placeholder)
        .into(this)
}
