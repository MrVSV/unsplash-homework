package com.example.unsplashhomework.presentation.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.example.unsplashhomework.databinding.FragmentPagingPhotosBinding
import com.example.unsplashhomework.presentation.collections.BaseFragment

class PhotosPagingFragment : BaseFragment<FragmentPagingPhotosBinding>() {

    override fun initBinding(inflater: LayoutInflater) =
        FragmentPagingPhotosBinding.inflate(inflater)

    private val viewModel by viewModels<PhotosViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
    }

    private fun observe(){
        viewModel.text.observe(viewLifecycleOwner){
            binding.textHome.text = it
        }
    }

}