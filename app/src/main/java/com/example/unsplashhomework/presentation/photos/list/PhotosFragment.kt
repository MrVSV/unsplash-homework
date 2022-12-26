package com.example.unsplashhomework.presentation.photos.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.unsplashhomework.data.model.Photo
import com.example.unsplashhomework.databinding.FragmentPhotosBinding
import com.example.unsplashhomework.presentation.photos.list.adapter.ClickableView
import com.example.unsplashhomework.presentation.photos.list.adapter.PhotoPagingAdapter
import com.example.unsplashhomework.tools.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhotosFragment : BaseFragment<FragmentPhotosBinding>() {

    override fun initBinding(inflater: LayoutInflater) =
        FragmentPhotosBinding.inflate(inflater)

    private val viewModel by viewModels<PhotosViewModel>()

    private val adapter by lazy {
        PhotoPagingAdapter { buttonState, item ->
            onClick(buttonState, item)
        }
    }

    private fun onClick(buttonState: ClickableView, item: Photo) {
        when (buttonState) {
            ClickableView.PHOTO -> {
                findNavController().navigate(
                    PhotosFragmentDirections
                        .actionNavigationPhotosToNavigationPhotoDetails(item.id)
                )
            }
            ClickableView.LIKE -> {
                viewModel.like(item)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
        binding.photoRecycler.adapter = adapter
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getPhoto().collect {
                adapter.submitData(it)
            }
        }
    }
}
