package com.example.unsplashhomework.presentation.photos.details

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.unsplashhomework.domain.LikeDetailPhotoUseCaseImpl
import com.example.unsplashhomework.domain.OnePhotoDetailsUseCaseImpl
import com.example.unsplashhomework.domain.model.PhotoDetails
import com.example.unsplashhomework.tools.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


//TODO:здесь должна быть зависимость на интерфейсы юзкейсов, переписать,
// когда разберемся, где это все провайдить, без провайда не билдится
@HiltViewModel
class OnePhotoDetailsViewModel @Inject constructor(
    private val onePhotoDetailsUseCase: OnePhotoDetailsUseCaseImpl,
    private val likeDetailPhotoUseCase: LikeDetailPhotoUseCaseImpl
) : BaseViewModel() {

    private val _state = MutableStateFlow<DetailsState>(DetailsState.NotStartedYet)
    val state = _state.asStateFlow()

    //private val _downloadState = MutableStateFlow<DownloadState>(DownloadState.NotFinished)
    //val downloadState = _downloadState.asStateFlow()

    var downloadID = 0L
    var dMStatus: Int? = null

    fun loadPhotoDetails(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val a = onePhotoDetailsUseCase.getPhotoDetails(id = id)
                _state.value = DetailsState.Success(a)
            } catch (_: Exception) {
                _state.value = DetailsState.LoadingError
            }
        }
    }

    fun like(item: PhotoDetails) {
        viewModelScope.launch(Dispatchers.IO + handler) {
            likeDetailPhotoUseCase.likeDetailPhoto(item)
            val a = onePhotoDetailsUseCase.getPhotoDetails(id = item.id)
            _state.value = DetailsState.Success(a)

        }
    }

    // fun startDownLoad(url: String, downloadManager: DownloadManager) {
//        var dnlMan = downloadManager
//        val downloadRequest = DownloadManager.Request(Uri.parse(url))
//            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
//            .setTitle("Unsplash photo")
//            .setDestinationInExternalPublicDir(
//                Environment.DIRECTORY_DOWNLOADS,
//                File.separator + "fromUnsplash.jpg"
//            )
//            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//
//        // downloadManager.enqueue(downloadRequest)
//
//        downloadID =
//            downloadManager.enqueue(downloadRequest) // enqueue puts the download request in the queue.
//
//        /**for testing snack with open*/
//        _downloadState.value = DownloadState.Finished
//    }


//        getDMStatus(dnlMan)}

/*
    fun getDMStatus(dnlMan: DownloadManager): Int? {
        val request = DownloadManager.Query()
            .setFilterById(downloadID)
        dnlMan.query(request).use { it ->
            _DMStatus = if (it.count > 0) {
                it.getInt(
                    //было getColumnIndex(...)
                    it.getColumnIndex(DownloadManager.COLUMN_STATUS)
                )
            } else null
        }
        return _DMStatus
    }*/

    @SuppressLint("Range")
    fun startDownLoad(url: String, downloadManager: DownloadManager) {
        var flag = true
        var downloading = true
        var mManager: DownloadManager? = downloadManager
        viewModelScope.launch(Dispatchers.IO) {
            try {


                // val mRqRequest = DownloadManager.Request(Uri.parse(url))
                //val idDownLoad = mManager!!.enqueue(mRqRequest)
                //var query: DownloadManager.Query? = null
                //var query = DownloadManager.Query()

                val downloadRequest = DownloadManager.Request(Uri.parse(url))
                    .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
                    .setTitle("Unsplash photo")
                    .setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS,
                        File.separator + "fromUnsplash.jpg"
                    )
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

                //downloadManager.enqueue(downloadRequest)

                //val idDownLoad =
                downloadID = downloadManager.enqueue(downloadRequest)
                val query = DownloadManager.Query()
                var c: Cursor?
                query.setFilterByStatus(DownloadManager.STATUS_FAILED or DownloadManager.STATUS_PAUSED or DownloadManager.STATUS_SUCCESSFUL or DownloadManager.STATUS_RUNNING or DownloadManager.STATUS_PENDING)
                while (downloading) {
                    c = mManager!!.query(query)
                    if (c.moveToPosition(0)) { //moveToFirst()) {
                        //Log.i("FLAG", "Downloading")
                        val status: Int = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))
                        if (status == DownloadManager.STATUS_SUCCESSFUL) {
                            Log.i("Kart", "done")
                            downloading = false
                            flag = true
                            c.close()
                            break
                        }
                        if (status == DownloadManager.STATUS_FAILED) {
                            Log.i("Kart", "Fail")
                            downloading = false
                            flag = false
                            c.close()
                            break
                        }
                    } else {
                        c.close()
                    }
                    //c.close()
                   // mManager = null
                }

            } catch (e: java.lang.Exception) {
                flag = false
            }
        }
        mManager = null
    }
}