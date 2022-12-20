package com.example.unsplashhomework.presentation.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.unsplashhomework.databinding.FragmentPagingPhotosBinding
import com.example.unsplashhomework.presentation.collections.BaseFragment
import kotlinx.coroutines.launch

class PhotosPagingFragment : BaseFragment<FragmentPagingPhotosBinding>() {

    override fun initBinding(inflater: LayoutInflater) =
        FragmentPagingPhotosBinding.inflate(inflater)

    private val viewModel by viewModels<PhotosViewModel>()

//    val navArgs: PhotosPagingFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel.createToken(navArgs.code)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.a.collect {
                binding.textHome.text = "token = ${it.access_token}"
            }
        }
//        binding.textHome.text = navArgs.code
    }
}
