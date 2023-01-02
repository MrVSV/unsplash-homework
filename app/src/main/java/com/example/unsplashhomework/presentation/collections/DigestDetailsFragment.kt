package com.example.unsplashhomework.presentation.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.unsplashhomework.R
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
        getLoadingState()
        loadStateItemsObserve()
        loadStateLike()
        settingAdapter()
        initRefresher()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.setId(args.id) { adapter.refresh() }
            viewModel.getPhoto().collect { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun getLoadingState() {
        viewModel.getDigestInfo(args.id)
        viewLifecycleOwner.lifecycleScope
            .launchWhenStarted {
                viewModel.loadState.collect { loadState -> updateUiOnServerResponse(loadState) }
            }
    }

    private fun updateUiOnServerResponse(loadState: LoadState) {
        if (loadState == LoadState.ERROR) {
            binding.error.isVisible = true
        }
        if (loadState == LoadState.SUCCESS) {
            viewLifecycleOwner.lifecycleScope
                .launchWhenStarted {
                    viewModel.state
                        .collect { state -> showInfo(state) }
                }
        }
    }

    private fun showInfo(state: DigestState) {
        when (state) {
            DigestState.NotStartedYet -> {
                binding.toolProgressBar.visibility = View.VISIBLE
            }
            is DigestState.Success -> {
                binding.toolProgressBar.visibility = View.GONE
                binding.collapsingToolbarLayout.title = state.data.title
                binding.digestTitle.text = state.data.title
                binding.description.text = state.data.description
                binding.tags.text = state.data.tags.joinToString { tag ->
                    "#${tag.title}"
                }
                binding.data.text =
                    getString(R.string.digest_data, state.data.totalPhotos, state.data.userUsername)
                binding.preview.loadImage(state.data.previewPhoto)
            }
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
            binding.recyclerProgressBar.isVisible =
                loadState.mediator?.refresh is androidx.paging.LoadState.Loading
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