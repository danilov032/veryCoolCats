package com.example.catstestapp.ui

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.core.content.ContextCompat
import com.example.catstestapp.models.Cat
import io.reactivex.Observable
import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

class DownloadRepository(
    private val context: Context
) {

    private val manager: DownloadManager by lazy { context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager }

    fun checkPermission(): Boolean {
        val permissionStatus =
            ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permissionStatus == -1) return false
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED
        } else {
            true
        }
    }

    fun saveCat(cat: Cat): Boolean {
        val url = cat.url
        val request = DownloadManager.Request(Uri.parse(url))
        val extension = url.substring(url.lastIndexOf("."))

        val files = getFilesDownload()
        val fileSearch = File(
            Environment.getExternalStorageDirectory()
                .toString() + "/" + Environment.DIRECTORY_DOWNLOADS.toString() + "/" + url.md5() + extension
        )

        if (files.contains(fileSearch))
            return false

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle(url.md5())
        request.setDescription("the file...")

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            url.md5() + extension
        )
        manager.enqueue(request)
        return true
    }

    fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }

    private fun getFilesDownload(): kotlin.Array<File> {
        val path = File(
            Environment.getExternalStorageDirectory()
                .toString() + "/" + Environment.DIRECTORY_DOWNLOADS.toString()
        )
        return path.listFiles()
    }

    fun getFilesDownloadO(): Observable<List<File>> {
        val path = File(
            Environment.getExternalStorageDirectory()
                .toString() + "/" + Environment.DIRECTORY_DOWNLOADS.toString()
        )
        return Observable.just(listOf())
//        return  Observable.just(path.listFiles().toList())
    }
}