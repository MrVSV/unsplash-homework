package com.example.unsplashhomework.presentation.photos.list

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.unsplashhomework.data.model.Photo
import com.example.unsplashhomework.data.state.ClickableView
import com.example.unsplashhomework.data.state.LoadState
import com.example.unsplashhomework.databinding.FragmentPhotosBinding
import com.example.unsplashhomework.presentation.photos.list.adapter.PagingPhotoAdapter
import com.example.unsplashhomework.tools.BaseFragment
import com.example.unsplashhomework.tools.setChangeTextListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PhotosFragment : BaseFragment<FragmentPhotosBinding>() {

    override fun initBinding(inflater: LayoutInflater) =
        FragmentPhotosBinding.inflate(inflater)

    private val viewModel by viewModels<PhotosViewModel>()

    private val adapter by lazy {
        PagingPhotoAdapter { buttonState, item ->
            onClick(buttonState, item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
        loadStateItemsObserve()
        loadStateLick()
        settingAdapter()
        setSearchView()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getPhoto().collect { pagingData ->
                adapter.submitData(pagingData)
                Log.d(TAG, "observe: $pagingData")
            }
        }
    }

    private fun onClick(buttonState: ClickableView, item: Photo) {
        val action =
            PhotosFragmentDirections.actionNavigationPhotosToNavigationPhotoDetails(item.id)
        when (buttonState) {
            ClickableView.PHOTO ->
                findNavController().navigate(action)
            ClickableView.LIKE -> viewModel.like(item)
        }
    }

    private fun setSearchView() {
        val searchView = binding.searchBar.menu.getItem(0).actionView as SearchView
        searchView.setChangeTextListener { query ->
            viewModel.setQuery(query) { adapter.refresh() }
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

    private fun loadStateLick() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loadState.collect { loadStateLike ->
                binding.error.isVisible =
                    loadStateLike == LoadState.ERROR
            }
        }
    }
}
