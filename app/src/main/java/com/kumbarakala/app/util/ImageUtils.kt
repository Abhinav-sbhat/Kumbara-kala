package com.kumbarakala.app.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

/**
 * Utility for image compression, scaling, and file operations.
 */
object ImageUtils {

    /**
     * Convert a URI to a Bitmap.
     */
    fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            inputStream?.use { stream ->
                BitmapFactory.decodeStream(stream)
            }
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Compress and resize a bitmap to max dimensions while maintaining aspect ratio.
     */
    fun compressImage(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height

        if (width <= maxWidth && height <= maxHeight) {
            return bitmap
        }

        val ratio = minOf(
            maxWidth.toFloat() / width,
            maxHeight.toFloat() / height
        )

        val newWidth = (width * ratio).toInt()
        val newHeight = (height * ratio).toInt()

        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
    }

    /**
     * Center-crop a bitmap to the target aspect ratio.
     */
    fun centerCrop(bitmap: Bitmap, targetWidth: Int, targetHeight: Int): Bitmap {
        val sourceWidth = bitmap.width
        val sourceHeight = bitmap.height

        val targetRatio = targetWidth.toFloat() / targetHeight
        val sourceRatio = sourceWidth.toFloat() / sourceHeight

        val cropWidth: Int
        val cropHeight: Int

        if (sourceRatio > targetRatio) {
            // Source is wider — crop width
            cropHeight = sourceHeight
            cropWidth = (sourceHeight * targetRatio).toInt()
        } else {
            // Source is taller — crop height
            cropWidth = sourceWidth
            cropHeight = (sourceWidth / targetRatio).toInt()
        }

        val x = (sourceWidth - cropWidth) / 2
        val y = (sourceHeight - cropHeight) / 2

        val cropped = Bitmap.createBitmap(bitmap, x, y, cropWidth, cropHeight)
        return Bitmap.createScaledBitmap(cropped, targetWidth, targetHeight, true)
    }

    /**
     * Save a bitmap to a file as JPEG.
     */
    fun saveBitmapToFile(bitmap: Bitmap, file: File, quality: Int = 85) {
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)
        }
    }

    /**
     * Load a bitmap from a local file path.
     */
    fun loadBitmapFromPath(path: String): Bitmap? {
        return try {
            val file = File(path)
            if (file.exists()) {
                BitmapFactory.decodeFile(path)
            } else null
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Create a bitmap with specific dimensions.
     */
    fun createBitmap(width: Int, height: Int): Bitmap {
        return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    }
}
