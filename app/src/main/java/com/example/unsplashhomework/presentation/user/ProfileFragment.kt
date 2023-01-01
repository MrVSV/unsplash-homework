package com.example.unsplashhomework.presentation.user

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.unsplashhomework.data.api.TOKEN_ENABLED_KEY
import com.example.unsplashhomework.data.api.TOKEN_SHARED_KEY
import com.example.unsplashhomework.data.api.TOKEN_SHARED_NAME
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

    override fun initBinding(inflater: LayoutInflater) = FragmentProfileBinding.inflate(inflater)

    private val viewModel by viewModels<ProfileViewModel>()

    private val adapter by lazy {
        PhotoPagingAdapter { buttonState, item ->
            onClick(buttonState, item)
        }
    }

    private var location: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
        showInfo()
        loadStateItemsObserve()
        loadStateLike()
        settingAdapter()
        initRefresher()
        setLocationClick()
        setUpLogoutButton(createSharedPreference(TOKEN_SHARED_NAME))
//        setUpAlertDialog()
    }

    private fun observe() {
        Log.d(TAG, "observe: ")
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getPhoto().collect { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun showInfo() {
        viewLifecycleOwner.lifecycleScope.launch {
            val profileInfo = viewModel.getProfile()
            viewModel.setUsername(profileInfo.userName) { adapter.refresh() }
            binding.location.text = profileInfo.location
            binding.username.text = profileInfo.userName
            binding.name.text = profileInfo.name
            binding.likes.text = "likes: ${profileInfo.totalLikes}"
            binding.avatar.loadImage(profileInfo.avatar)
            location = profileInfo.location
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

    private fun showLocationOnMap(locationUri: Uri) {
        val mapIntent = Intent(Intent.ACTION_VIEW).apply { data = locationUri }
        startActivity(mapIntent)
    }

    private fun setLocationClick() {
        binding.locationButton.setOnClickListener {
            if (location != null) {
                showLocationOnMap(Uri.parse("geo:0,0?q=$location"))
            }
        }
    }

    private fun setUpLogoutButton(preferences: SharedPreferences) {
        val button = binding.logoutBar.menu.getItem(0)
        button?.setOnMenuItemClickListener {
            setUpAlertDialog(preferences)
            true
        }
    }

    private fun setUpAlertDialog(preferences: SharedPreferences) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Выйти из профиля?")
            .setMessage("Все локальные данные будут удалены")
            .setPositiveButton("Да") { _, _ ->
                preferences.edit().putString(TOKEN_SHARED_KEY, "").apply()
                preferences.edit().putBoolean(TOKEN_ENABLED_KEY, false).apply()
//                Log.d(TAG, "setUpLogoutButton: ${preferences.getString(TOKEN_ENABLED_KEY, "")}")
                val action = ProfileFragmentDirections.actionNavigationUserToAuthFragment()
                findNavController().navigate(action)
            }
            .setNegativeButton("Нет") { _, _ ->
                dialog.create().hide()
            }
        dialog.create().show()
    }
}
