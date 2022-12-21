package com.example.unsplashhomework.presentation.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.unsplashhomework.data.remote.ClickableView
import com.example.unsplashhomework.data.remote.PhotosModel
import com.example.unsplashhomework.data.remote.PhotosPagingAdapter
import com.example.unsplashhomework.databinding.FragmentPhotosBinding
import com.example.unsplashhomework.presentation.collections.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhotosFragment() : BaseFragment<FragmentPhotosBinding>() {

    override fun initBinding(inflater: LayoutInflater) =
        FragmentPhotosBinding.inflate(inflater)

    private val viewModel by viewModels<PhotosViewModel>()
    private val navArgs: PhotosFragmentArgs by navArgs()

    private val adapter =
        PhotosPagingAdapter { buttonState, item -> onItemClick(buttonState, item) }

    /**пока что не работает. не меняется статус вьюхи**/
    private fun onItemClick(buttonState: ClickableView, item: PhotosModel.PhotosModelItem) {
            when (buttonState) {
                ClickableView.PHOTO -> {
//                findNavController()
                }
                ClickableView.LIKE -> {
                    viewModel.like(item.id, item.likedByUser)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.createToken(navArgs.code)
        binding.photoRecycler.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.a.collect {
                adapter.submitData(it)
            }
        }

    }

}
