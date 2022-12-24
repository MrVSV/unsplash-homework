package com.example.unsplashhomework.presentation.photos

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.unsplashhomework.R
import com.example.unsplashhomework.databinding.FragmentOnePhotoDetailsBinding
import com.example.unsplashhomework.tools.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnePhotoDetailsFragment : BaseFragment<FragmentOnePhotoDetailsBinding>() {

    override fun initBinding(inflater: LayoutInflater) =
        FragmentOnePhotoDetailsBinding.inflate(inflater)

    private val viewModel: OnePhotoDetailsViewModel by viewModels()
    private val args by navArgs<OnePhotoDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getPhotoDetails(args.photoId)

        viewLifecycleOwner.lifecycleScope
            .launchWhenStarted {
                viewModel.state
                    .collect { state -> updateUi(state) }
            }
    }

    private fun getPhotoDetails(id: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            //  viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.loadPhotoDetails(id)
        }
        //}
    }

    private fun updateUi(state: DetailsState) {
        when (state) {
            DetailsState.NotStartedYet -> {}
            is DetailsState.Success -> {
                binding.authorName.text = state.data.user.first_name + state.data.user.name
                binding.authorAccount.text = "@" + state.data.user.username
                //binding.isLiked = ?

                binding.location.text = state.data.location.city
                binding.currentLikes.text = state.data.likes.toString()

                binding.tags.text = state.data.tags.joinToString { tag ->
                    "#${tag.title ?: "N/A"}"
                }
                binding.exif.text = "Made with: " + state.data.exif.model

                binding.aboutAuthor.text = getString(
                    R.string.about, state.data.user.username, state.data.user.bio ?: "N/A"
                )

                binding.downloadsCount.text = "Download (" + state.data.downloads.toString() + ")"
                loadImages(state)

                val lat = state.data.location.position.latitude
                val lon = state.data.location.position.longitude

                val onLocationClick: (lat: Double, lon: Double) -> Unit = { lat, lon ->
                    val locationUri = Uri.parse("geo: $lat,$lon")
                    showLocationInMap(locationUri)
                }

                binding.locationButton.setOnClickListener {
                    if (lat != null && lon != null) {
                        onLocationClick(lat, lon)
                    }
                }
            }
            is DetailsState.LoadingError -> {
                Toast.makeText(context, "Loading Error", Toast.LENGTH_SHORT).show()
                //binding.loading.text = "Ошибка сети"
            }
        }
    }

    private fun loadImages(state: DetailsState.Success) {
        Glide
            .with(binding.photo.context)
            .load(state.data.urls.regular)
            .into(binding.photo)
        Glide
            .with(binding.authorAvatar.context)
            .load(state.data.user.profile_image.small)
            .into(binding.authorAvatar)
    }

    private fun showLocationInMap(locationUri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = locationUri
        }
        if (activity?.packageManager != null) {
            startActivity(intent)
        }
    }
}
