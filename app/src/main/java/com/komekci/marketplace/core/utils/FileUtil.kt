package com.komekci.marketplace.core.utils

import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import java.io.File

class FileUtil {
    companion object {
        fun getFileFromUri(uri: Uri, contentResolver: ContentResolver): File? {
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = contentResolver.query(uri, filePathColumn, null, null, null)
            cursor?.moveToFirst()
            val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
            val filePath = cursor?.getString(columnIndex!!)
            cursor?.close()
            Log.e("PATH", filePath?:"")
            return filePath?.let { File(it) }
        }
    }
}