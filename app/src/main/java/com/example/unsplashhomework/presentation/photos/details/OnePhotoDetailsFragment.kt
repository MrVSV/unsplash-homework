package com.example.unsplashhomework.presentation.photos.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadPhotoDetails(id)
            }
        }
    }

    private fun updateUi(state: DetailsState) {
        when (state) {
            DetailsState.NotStartedYet -> {}
            is DetailsState.Success -> {
                binding.authorName.text = state.data.user.name
                binding.authorAccount.text = "@" + state.data.user.username
                //binding.isLiked = ?

                binding.location.text = state.data.location.city ?: "N/A"
                binding.currentLikes.text = state.data.likes.toString()

                binding.tags.text = state.data.tags.joinToString { tag ->
                    "#${tag.title ?: "N/A"}"
                }
                binding.exif.text = buildExifText(state)
                binding.aboutAuthor.text = getString(
                    R.string.about, state.data.user.username, state.data.user.bio ?: "N/A"
                )

                binding.downloadsCount.text = getString(
                    R.string.download,
                    state.data.downloads.toString()
                )
                loadImages(state)

                binding.downloadButton.setOnClickListener {
                    //TODO: check permissions, download to gallery
                }

                val lat = state.data.location.position.latitude
                val lon = state.data.location.position.longitude

                val onLocationClick: (lat: Double, lon: Double) -> Unit = { lat, lon ->
                    val locationUri = Uri.parse("geo: $lat,$lon")
                    showLocationOnMap(locationUri)
                }

                binding.locationButton.setOnClickListener {
                    if (lat != null && lon != null) {
                        onLocationClick(lat, lon)
                    }
                }
                setToolbar(state.data.id)
            }
            is DetailsState.LoadingError -> {
                Toast.makeText(context, "Loading Error", Toast.LENGTH_SHORT).show()
                //binding.loading.text = "Ошибка сети"
            }
        }
    }

    private fun buildExifText(state: DetailsState.Success): String {
        return buildString {
            append(getString(R.string.made_with, state.data.exif.make ?: "N/A"))
            append(getString(R.string.model, state.data.exif.model ?: "N/A"))
            append(getString(R.string.exposure, state.data.exif.exposureTime ?: "N/A"))

            append(getString(R.string.aperture, state.data.exif.aperture ?: "N/A"))
            append(getString(R.string.focal_length, state.data.exif.focalLength ?: "N/A"))
            append(getString(R.string.iso, if (state.data.exif.iso !== null) state.data.exif.iso.toString() else "N/A"))
        }
    }

    private fun loadImages(state: DetailsState.Success) {
        Glide
            .with(binding.photo.context)
            .load(state.data.urls.regular) //raw грузиться будет дольше, но его потом надо скачивать
            .into(binding.photo)
        Glide
            .with(binding.authorAvatar.context)
            .load(state.data.user.profileImage.small)
            .into(binding.authorAvatar)
    }

    private fun showLocationOnMap(locationUri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = locationUri
        }
        if (activity?.packageManager != null) {
            startActivity(intent)
        }
    }

    private fun setToolbar(id: String) {
        binding.toolbar.setOnClickListener {
            shareLinkOnPhoto(id)
        }
    }

    private fun shareLinkOnPhoto(id: String) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "https://unsplash.com/photos/$id")
        startActivity(Intent.createChooser(sharingIntent, "Share using"))
    }
}
