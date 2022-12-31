package com.example.unsplashhomework.presentation.user

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.unsplashhomework.data.state.ClickableView
import com.example.unsplashhomework.data.state.LoadState
import com.example.unsplashhomework.databinding.FragmentProfileBinding
import com.example.unsplashhomework.domain.model.Photo
import com.example.unsplashhomework.presentation.photos.list.adapter.PhotoPagingAdapter
import com.example.unsplashhomework.tools.BaseFragment
import com.example.unsplashhomework.tools.loadImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override fun initBinding(inflater: LayoutInflater)= FragmentProfileBinding.inflate(inflater)

    private val viewModel by viewModels<ProfileViewModel>()

    private val adapter by lazy {
        PhotoPagingAdapter { buttonState, item ->
            onClick(buttonState, item)
        }
    }

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
        Log.d(TAG, "observe: ")
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getPhoto().collect { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun showInfo(){
        viewLifecycleOwner.lifecycleScope.launch {
            val profileInfo = viewModel.getProfile()
            viewModel.setUsername(profileInfo.userName) {adapter.refresh()}
            binding.location.text = profileInfo.location
            binding.username.text = profileInfo.userName
            binding.name.text = profileInfo.name
            binding.likes.text = "likes: ${profileInfo.totalLikes}"
            binding.avatar.loadImage(profileInfo.avatar)
        }

    }

    private fun onClick(buttonState: ClickableView, item: Photo) {
        val action =
            ProfileFragmentDirections.actionNavigationUserToNavigationPhotoDetails(item.id)
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