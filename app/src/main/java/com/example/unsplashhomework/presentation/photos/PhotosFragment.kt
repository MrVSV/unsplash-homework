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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhotosFragment : BaseFragment<FragmentPhotosBinding>() {

    override fun initBinding(inflater: LayoutInflater) =
        FragmentPhotosBinding.inflate(inflater)

    private val viewModel by viewModels<PhotosViewModel>()
    private val navArgs: PhotosFragmentArgs by navArgs()
    private val photosPagingAdapter = PhotosPagingAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.createToken(navArgs.code)
        binding.photoRecycler.adapter = photosPagingAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.a.collectLatest {
                binding.textHome.text = "token = ${it.access_token}"
//                AuthTokenProvider.authToken = it.access_token
            }
            viewModel.photo.collectLatest {
                launch(Dispatchers.Main) {
                    photosPagingAdapter.loadStateFlow.collectLatest { loadState ->
                        if (loadState.refresh is LoadState.Loading ||
                            loadState.append is LoadState.Loading
                        ) {
//                            binding.progressBar.visibility = View.VISIBLE
//                            binding.btnReload.visibility = View.GONE
                        } else {
//                            binding.progressBar.visibility = View.GONE
//                            binding.btnReload.visibility = View.GONE
                            val errorState = when {
                                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                                else -> null
                            }
                            errorState?.let {
                                Log.d("Kart", "fragment: ${it.error.message.toString()}")
//                                binding.btnReload.visibility = View.VISIBLE
                                Toast.makeText(requireContext(),
                                    it.error.toString(),
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

                photosPagingAdapter.submitData(it)
            }
        }
    }
}
