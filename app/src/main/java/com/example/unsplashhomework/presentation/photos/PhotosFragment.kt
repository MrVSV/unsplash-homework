package com.example.unsplashhomework.presentation.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.unsplashhomework.data.remote.ClickableView

import com.example.unsplashhomework.data.remote.PhotosPagingAdapter
import com.example.unsplashhomework.data.remote.photosmodel.PhotosModelItem

import com.example.unsplashhomework.databinding.FragmentPhotosBinding
import com.example.unsplashhomework.tools.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhotosFragment() : BaseFragment<FragmentPhotosBinding>() {

    override fun initBinding(inflater: LayoutInflater) =
        FragmentPhotosBinding.inflate(inflater)

    private val viewModel by viewModels<PhotosViewModel>()
//    private val navArgs: PhotosFragmentArgs by navArgs()

    private val adapter =
        PhotosPagingAdapter { buttonState, item -> onItemClick(buttonState, item) }

    /**пока что не работает. не меняется статус вьюхи**/
    private fun onItemClick(buttonState: ClickableView, item: PhotosModelItem) {
            when (buttonState) {
                ClickableView.PHOTO -> {
                findNavController().navigate(PhotosFragmentDirections.actionNavigationPhotosToNavigationPhotoDetails(item.id))
                }
                ClickableView.LIKE -> {
                    viewModel.like(item.id, item.likedByUser)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.photoRecycler.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getPhoto()
            viewModel.a.collect {
                adapter.submitData(it)
            }
        }

    }

}
