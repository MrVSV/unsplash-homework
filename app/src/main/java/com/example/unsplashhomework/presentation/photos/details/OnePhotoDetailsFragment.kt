package com.example.unsplashhomework.presentation.photos.details

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.DownloadManager
import android.content.*
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.RECEIVER_EXPORTED
import androidx.core.content.ContextCompat.registerReceiver
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.unsplashhomework.R
import com.example.unsplashhomework.data.state.LoadState
import com.example.unsplashhomework.databinding.FragmentOnePhotoDetailsBinding
import com.example.unsplashhomework.domain.model.PhotoDetails
import com.example.unsplashhomework.tools.BaseFragment
import com.example.unsplashhomework.tools.loadImage
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class OnePhotoDetailsFragment : BaseFragment<FragmentOnePhotoDetailsBinding>() {

    override fun initBinding(inflater: LayoutInflater) =
        FragmentOnePhotoDetailsBinding.inflate(inflater)

    private val viewModel: OnePhotoDetailsViewModel by viewModels()
    private val args by navArgs<OnePhotoDetailsFragmentArgs>()

    private var lat: Double? = null
    private var lon: Double? = null

    private val downloadManager by lazy {
        requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }

    private var enableDownloadFlag = false

    private val launcher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { map ->
        if (map.values.isNotEmpty() && map.values.all { it }) {
            Toast.makeText(context, "Permissions granted", Toast.LENGTH_SHORT).show()
            enableDownloadFlag = true
        } else {
            showMissingPermissionAlert()
        }
    }
/*
    private val downloadID = viewModel.downloadID

    private val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            //Fetching the download id received with the broadcast
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadID == id) {
                Toast.makeText(context, "Download Completed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(App.applicationContext(),onDownloadComplete,IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE), RECEIVER_EXPORTED)

    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadPhotoDetails(args.photoId)
        getCurrentState()
        setLocationClick()
        loadStateLike()

        val filter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        val receiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                val reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (viewModel.downloadID == reference) {
                    // Do something with downloaded file.
                    Log.d("Kart", "референс тот")
                    //if (viewModel.getDMStatus(downloadManager) == STATUS_SUCCESSFUL)
                    //{showSnackbar()} else {Log.d("Kart", "ужос")}
                    showSnackbar()
                }
            }
        }
        registerReceiver(requireContext(), receiver, filter, RECEIVER_EXPORTED)
    }


    private fun getCurrentState() {
        viewLifecycleOwner.lifecycleScope
            .launchWhenStarted {
                viewModel.state
                    .collect { state -> updateUi(state) }
            }
    }

/*    private fun getDownloadState(raw: String, downloadManager: DownloadManager) {
        viewLifecycleOwner.lifecycleScope
            .launchWhenStarted {
                viewModel.downloadState
                    .collect { downState ->
                        delay(10_000)
                        showSnackbar(downState) }
            }
    }*/

    private fun updateUi(state: DetailsState) {
        when (state) {
            DetailsState.NotStartedYet -> {}
            is DetailsState.Success -> {

                bindUploadedTexts(state)
                bindUploadedImages(state)
                setUploadedLocation(state)
                setToolbar(state.data.id)
                setLikeClick(state.data)
                setDownloadOnClick(state.data.urls.raw, downloadManager)
            }
            is DetailsState.LoadingError -> {
                Toast.makeText(context, "Loading Error", Toast.LENGTH_SHORT).show()
                //binding.loading.text = "Ошибка сети"
            }
        }
    }

    private fun bindUploadedTexts(state: DetailsState.Success) {
        binding.authorName.text = state.data.user.name
        binding.authorAccount.text = "@" + state.data.user.username

        binding.location.text = state.data.location.city ?: "N/A"
        binding.currentLikes.text = state.data.likes.toString()
        binding.isLiked.isSelected = state.data.likedByUser

        binding.tags.text = state.data.tags.joinToString { tag ->
            "#${tag.title ?: "N/A"}"
        }
        binding.exif.text = buildExifText(state)
        binding.aboutAuthor.text = getString(
            R.string.about, state.data.user.username, state.data.user.bio ?: "N/A"
        )

        binding.downloadsCount.text = getString(
            R.string.download, state.data.downloads.toString()
        )
    }

    private fun bindUploadedImages(state: DetailsState.Success) {
        binding.photo.loadImage(state.data.urls.regular)
        binding.authorAvatar.loadImage(state.data.user.profileImage.small)
    }

    private fun setUploadedLocation(state: DetailsState.Success) {
        if (state.data.location.position.latitude != null && state.data.location.position.longitude != null) {
            lat = state.data.location.position.latitude
            lon = state.data.location.position.longitude
        }
    }

    private fun setLocationClick() {
        binding.locationButton.setOnClickListener {
            if (lat != null && lon != null) {
                showLocationOnMap(Uri.parse("geo: $lat,$lon"))
            }
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

    private fun setDownloadOnClick(url: String, downloadManager: DownloadManager) {
        binding.downloadButton.setOnClickListener {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                checkPermission()
            } else {
                enableDownloadFlag = true
            }
            if (enableDownloadFlag) {
                viewModel.startDownLoad(url, downloadManager)
                //pickDownloadState(downloadManager)
            }
        }
    }
    //    fun pickDownloadState(downloadManager: DownloadManager) {
//        val status = viewModel.getDMStatus(downloadManager)
//        when (status) {
//            DownloadManager.STATUS_SUCCESSFUL -> showSnackbar()
//        }
//    }
    private fun showSnackbar() {
/*        when (state) {
            DownloadState.NotFinished -> {}
            DownloadState.Finished -> {*/

        val mySnackbar = Snackbar.make(
            binding.myCoordinatorLayout,
            getString(R.string.download_finished),
            Snackbar.LENGTH_INDEFINITE
        )
        mySnackbar.setAction(R.string.open, View.OnClickListener {
            val openIntent = Intent().apply {
                action = Intent.ACTION_VIEW
                type = "image/jpg" //только type без data открывает галерею
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            //val imgUri = Uri.fromFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS))
           // openIntent.setDataAndType(imgUri, "*/jpg")
            startActivity(openIntent)
        })
        mySnackbar.show()
    }

    private fun setLikeClick(photo: PhotoDetails) {
        binding.isLiked.setOnClickListener {
            viewModel.like(photo)
        }
    }

    private fun buildExifText(state: DetailsState.Success): String {
        return buildString {
            append(getString(R.string.made_with, state.data.exif.make ?: "N/A"))
            append(getString(R.string.model, state.data.exif.model ?: "N/A"))
            append(getString(R.string.exposure, state.data.exif.exposureTime ?: "N/A"))
            append(getString(R.string.aperture, state.data.exif.aperture ?: "N/A"))
            append(getString(R.string.focal_length, state.data.exif.focalLength ?: "N/A"))
            append(getString(R.string.iso, state.data.exif.iso?.toString() ?: "N/A"))
        }
    }

    private fun showLocationOnMap(locationUri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = locationUri
        }
        startActivity(intent)
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

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(context, "Permission already granted", Toast.LENGTH_SHORT).show()
            enableDownloadFlag = true
        } else {
            /**вот здесь упростить надо и убрать два клика для скачивания на апи <=28*/
            launcher.launch(arrayOf(WRITE_EXTERNAL_STORAGE))
        }
    }

    private fun showMissingPermissionAlert() {
        val alertDialog = AlertDialog.Builder(requireActivity().applicationContext).create()
        alertDialog.setTitle("Info")
        alertDialog.setMessage(getString(R.string.alert_text))
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert)
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok") { dialog, _ ->
            dialog.cancel()
        }
        alertDialog.show()
    }
}
