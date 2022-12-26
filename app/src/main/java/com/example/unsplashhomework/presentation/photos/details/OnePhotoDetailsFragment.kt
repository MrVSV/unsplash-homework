package com.example.unsplashhomework.presentation.photos.details

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
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
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import android.graphics.BitmapFactory
import android.os.*
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

        getPhotoDetails(args.photoId)
        /** это вынести в отдельный метод и я бы все таки разделил слушатель состоя ний и слушатель для данных
         * в слушателе состояний будет метод отвечабщий за визибилити ui а в слушателя данных будет бинд в ui
         * темболие стейт состояний уже написан и спрятан в базовой вью модели его можно и нужно от туда дергать*/
        viewLifecycleOwner.lifecycleScope
            .launchWhenStarted {
                viewModel.state
                    .collect { state -> updateUi(state) }
            }
    }

    /** вижу не в первый раз и не могу понять зачем и для чего?
     * что изменится если написать так?
     * private fun getPhotoDetails(id: String)=viewModel.loadPhotoDetails(id)

     давай просто разберемся что происходит.... ты запускаешь корутину в которой запускаешь еще корутину
     что бы вызвать метод из вью можели который запускает третью корутину*/

    private fun getPhotoDetails(id: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadPhotoDetails(id)
            }
        }
    }

    /** Первое что я говорил уже говорил разделить стейт и данные(темболее появляется не очень красивое is)
     * ...нужно взять за правило одна функция
     * одна опирация...темболие методо получилься большой и читать его сложновато
     * второе, если всетаки оставлять так, то вынеси каждое действие when в функции */
    private fun updateUi(state: DetailsState) {
        when (state) {
            DetailsState.NotStartedYet -> {}
            is DetailsState.Success -> {
                binding.authorName.text = state.data.user.name
                binding.authorAccount.text = "@" + state.data.user.username
                //TODO: поставить/убрать лайк
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
                /** у нас уже есть экстеншен для glide почему не использовать его?*/
                loadImages(state)

                /** я бы эти преременные сделал глобальные соответсвенно клик по кнопке не нужно будет
                 * описывать в стейте и вынести его можно отдельно*/
                val lat = state.data.location.position.latitude
                val lon = state.data.location.position.longitude

                /** очень хорошо что есть пониманя что такое колбек но тут он не уместен достаточно
                 * просто запонти переменные выше,
                 * кстати у тебя тут пременные и кликер в одном методе, не очень пойму щачем тут колбек*/
                val onLocationClick: (lat: Double, lon: Double) -> Unit = { lat, lon ->
                    val locationUri = Uri.parse("geo: $lat,$lon")
                    showLocationOnMap(locationUri)
                }
                /** как уже было сказано выше он тут не нужен, лучше сделатьб lat и lon глабальными
                 * и тогда этот клик будет обособленным...вот этот медод не зависит от стейта*/
  /**              binding.locationButton.setOnClickListener {
                    if (lat != null && lon != null) {
                        showLocationOnMap(Uri.parse("geo: $lat,$lon"))
                    }
                }*/
                binding.locationButton.setOnClickListener {
                    if (lat != null && lon != null) {
                        onLocationClick(lat, lon)
                    }
                }
                setToolbar(state.data.id)
                setDownload(state.data.urls.raw)
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
            append(
                getString(
                    R.string.iso, if (state.data.exif.iso !== null)
                        state.data.exif.iso.toString() else "N/A"
                )
            )
        }
    }

    /** к нас написан есть экстеншен для imageView это дублирование кода получется и нафиг тут не надо */
    private fun loadImages(state: DetailsState.Success) {
        Glide
            .with(binding.photo.context)
            .load(state.data.urls.regular)
            .into(binding.photo)
        Glide
            .with(binding.authorAvatar.context)
            .load(state.data.user.profileImage.small)
            .into(binding.authorAvatar)
    }

    /** я так понимаю просто что бы не писать свой фрагмент с навигацией? ничего против не имею)*/
    private fun showLocationOnMap(locationUri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = locationUri
        }
        /** зачем тут проверка?*/
        if (activity?.packageManager != null) {
            startActivity(intent)
        }
    }

    private fun setToolbar(id: String) {
        /** договоритесь с Сергеем что вы юзаете... просто ты написала свой тул бар
         * он на сколько я видел юзает его из активит*/
        binding.toolbar.setOnClickListener {
            shareLinkOnPhoto(id)
        }
    }

    /** просто ради сокращеня кода напеши экстеншен для фрашмента и тогда вот этого просто не будет тут
     * лежать */
    private fun shareLinkOnPhoto(id: String) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "https://unsplash.com/photos/$id")
        startActivity(Intent.createChooser(sharingIntent, "Share using"))
    }


    /** сюда не лезу...единственно что что либо убери отсюда сет он клик лисененр, и вызывай этот
     * методод внутри лиснера, либо переименую этот медот так что бы было понятно что
     * это именно действие по клику. Просто по названию должно быть четко понятно что это такое*/
    private fun setDownload(path: String) {
        var mImage: Bitmap?

        binding.downloadLine.setOnClickListener {
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
                    Handler(Looper.getMainLooper()).post {
                        if (mImage != null) {
                            saveMediaToStorage(mImage)
                        }
                    }
                }
            }
        }
    }

    /**не лезу... */
    //TODO: добавить загрузку в фоне, тестировать отсутствие сети
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

    /**опять такиж в виде жкстаншена лучше бы смотрелось */
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

    /**опять такиж в виде жкстаншена лучше бы смотрелось */
    private fun showMissingPermissionAlert() {
        val alertDialog = AlertDialog.Builder(requireActivity().applicationContext).create()
        alertDialog.setTitle("Info")
        alertDialog.setMessage("You have not given permission needed to save photo in the gallery. Please give permission and return back to the app.")
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert)
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok") { dialog, _ ->
            dialog.cancel()
        }
        alertDialog.show()
    }
}
