package com.example.unsplashhomework.presentation.photos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.example.unsplashhomework.data.remote.PhotosPagingAdapter
import com.example.unsplashhomework.databinding.FragmentPhotosBinding
import com.example.unsplashhomework.presentation.collections.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhotosFragment : BaseFragment<FragmentPhotosBinding>() {

    override fun initBinding(inflater: LayoutInflater) =
        FragmentPhotosBinding.inflate(inflater)

    private val viewModel by viewModels<PhotosViewModel>()
    private val navArgs: PhotosFragmentArgs by navArgs()
    private val adapter = PhotosPagingAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.createToken(navArgs.code)
        binding.photoRecycler.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.a.collect(){
                adapter.submitData(it)
            }
        }
    }
}
