package com.example.unsplashhomework.presentation.photos.details

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Looper
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.unsplashhomework.R
import com.example.unsplashhomework.domain.model.PhotoDetails
import com.example.unsplashhomework.data.state.LoadState
import com.example.unsplashhomework.databinding.FragmentOnePhotoDetailsBinding
import com.example.unsplashhomework.tools.BaseFragment
import com.example.unsplashhomework.tools.loadImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.Executors

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadPhotoDetails(args.photoId)
        getCurrentState()
        setLocationClick()
        loadStateLike()
    }

    private fun getCurrentState() {
        viewLifecycleOwner.lifecycleScope
            .launchWhenStarted {
                viewModel.state
                    .collect { state -> updateUi(state) }
            }
    }

    private fun updateUi(state: DetailsState) {
        when (state) {
            DetailsState.NotStartedYet -> {}
            is DetailsState.Success -> {

                bindUploadedTexts(state)
                bindUploadedImages(state)
                setUploadedLocation(state)
                setToolbar(state.data.id)
                setLikeClick(state.data)
                setDownloadClick(state.data.urls.raw, downloadManager)
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

    private fun setDownloadClick(url: String, downloadManager: DownloadManager){
        binding.downloadButton.setOnClickListener {
            viewModel.startDownLoad(url, downloadManager)
        }
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

//TODO: Переписать на download manager с нотификацией и загрузкой в фоне, тестировать отсутствие сети
    /** сюда не лезу...единственно что что либо убери отсюда сет он клик лисененр, и вызывай этот
     * методод внутри лиснера, либо переименую этот медот так что бы было понятно что
     * это именно действие по клику. Просто по названию должно быть четко понятно что это такое*/
    private fun setDownload(path: String) {
        var mImage: Bitmap?

        binding.downloadButton.setOnClickListener {
//TODO: после загрузки показать снэкбар по клику на который м.открыть фото в галерее
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                checkPermission()
            } else {
                enableDownloadFlag = true
            }
            if (enableDownloadFlag) {
                // executor will fetch the image and handler will store it locally
                Executors.newSingleThreadExecutor().execute {
                    mImage = loadImage(path)
                    android.os.Handler(Looper.getMainLooper()).post {
                        if (mImage != null) {
                            saveMediaToStorage(mImage)
                        }
                    }
                }
            }
        }
    }

    private fun saveMediaToStorage(bitmap: Bitmap?) {
        val filename = "${System.currentTimeMillis()}.jpg"
        var fileOutputStream: OutputStream? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            requireActivity().contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(
                        MediaStore.MediaColumns.RELATIVE_PATH,
                        Environment.DIRECTORY_PICTURES
                    )
                }
                val imageUri: Uri? = resolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues
                )
                fileOutputStream = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fileOutputStream = FileOutputStream(image)
        }
        fileOutputStream?.use {
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(context, "Saved to Gallery", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadImage(string: String): Bitmap? {
        val url: URL = convertStringToURL(string)!!
        val connection: HttpURLConnection?
        try {
            connection = url.openConnection() as HttpURLConnection
            connection.connect()
            return BitmapFactory.decodeStream(BufferedInputStream(connection.inputStream))
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
        }
        return null
    }

    private fun convertStringToURL(string: String): URL? {
        try {
            return URL(string)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return null
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
