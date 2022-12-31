package com.example.unsplashhomework.presentation.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.unsplashhomework.data.state.ClickableView
import com.example.unsplashhomework.data.state.LoadState
import com.example.unsplashhomework.databinding.FragmentDigestDetailsBinding
import com.example.unsplashhomework.domain.model.Photo
import com.example.unsplashhomework.presentation.photos.list.adapter.PhotoPagingAdapter
import com.example.unsplashhomework.tools.BaseFragment
import com.example.unsplashhomework.tools.loadImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DigestDetailsFragment : BaseFragment<FragmentDigestDetailsBinding>() {

    private val viewModel by viewModels<DigestDetailsViewModel>()

    private val adapter by lazy {
        PhotoPagingAdapter { buttonState, item ->
            onClick(buttonState, item)
        }
    }

    private val args by navArgs<DigestDetailsFragmentArgs>()

    override fun initBinding(inflater: LayoutInflater) =
        FragmentDigestDetailsBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
        showInfo()
        loadStateItemsObserve()
        loadStateLike()
        settingAdapter()
        initRefresher()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getPhoto(args.id).collect { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun showInfo(){
        viewLifecycleOwner.lifecycleScope.launch {
            val digestInfo = viewModel.getDigestInfo(args.id)
            binding.collapsingToolbarLayout.title = digestInfo.title
            binding.digestTitle.text = digestInfo.title
            binding.description.text = digestInfo.description
            binding.tags.text = digestInfo.tags.joinToString{ tag ->
                "#${tag.title}"
            }
            binding.data.text = "${digestInfo.totalPhotos} photos by ${digestInfo.userUsername}"
            binding.preview.loadImage(digestInfo.previewPhoto)
        }

    }

    private fun onClick(buttonState: ClickableView, item: Photo) {
        val action =
            DigestDetailsFragmentDirections.actionDigestDetailsFragmentToNavigationPhotoDetails(item.id)
        when (buttonState) {
            ClickableView.PHOTO ->
                findNavController().navigate(action)
            ClickableView.LIKE -> viewModel.like(item)
        }
    }

    private fun settingAdapter() {
        binding.photoRecycler.adapter = adapter
        binding.photoRecycler.itemAnimator?.changeDuration = 0
    }

    private fun loadStateItemsObserve() {
        adapter.addLoadStateListener { loadState ->
            binding.error.isVisible =
                loadState.mediator?.refresh is androidx.paging.LoadState.Error
        }
    }

    private fun loadStateLike() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loadState.collect { loadStateLike ->
                binding.error.isVisible =
                    loadStateLike == LoadState.ERROR
            }
        }
    }

    private fun initRefresher() {
        binding.swipeRefresh.setOnRefreshListener {
            binding.photoRecycler.isVisible = true
            adapter.refresh()
            binding.swipeRefresh.isRefreshing = false
        }
    }
}