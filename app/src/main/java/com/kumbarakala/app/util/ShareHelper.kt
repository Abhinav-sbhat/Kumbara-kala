package com.kumbarakala.app.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

/**
 * Utility for sharing card images via WhatsApp and other apps.
 */
object ShareHelper {

    /**
     * Share a card image specifically to WhatsApp.
     * Falls back to generic share chooser if WhatsApp is not installed.
     */
    fun shareToWhatsApp(context: Context, cardImagePath: String, caption: String = ""): Boolean {
        val file = File(cardImagePath)
        if (!file.exists()) return false

        val uri = getFileUri(context, file)

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/jpeg"
            putExtra(Intent.EXTRA_STREAM, uri)
            if (caption.isNotEmpty()) {
                putExtra(Intent.EXTRA_TEXT, caption)
            }
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            setPackage("com.whatsapp")
        }

        return try {
            context.startActivity(intent)
            true
        } catch (e: Exception) {
            // WhatsApp not installed — try WhatsApp Business
            try {
                intent.setPackage("com.whatsapp.w4b")
                context.startActivity(intent)
                true
            } catch (e2: Exception) {
                // Neither WhatsApp variant installed — fallback to generic share
                shareToOtherApps(context, cardImagePath, caption)
                true
            }
        }
    }

    /**
     * Share a card image via the generic Android share chooser.
     */
    fun shareToOtherApps(context: Context, cardImagePath: String, caption: String = "") {
        val file = File(cardImagePath)
        if (!file.exists()) return

        val uri = getFileUri(context, file)

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "image/jpeg"
            putExtra(Intent.EXTRA_STREAM, uri)
            if (caption.isNotEmpty()) {
                putExtra(Intent.EXTRA_TEXT, caption)
            }
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        val chooser = Intent.createChooser(intent, "Share Story Card")
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooser)
    }

    /**
     * Save a bitmap to the app's cache directory for sharing.
     */
    fun saveBitmapForSharing(context: Context, bitmap: Bitmap, fileName: String): File {
        val cardsDir = File(context.cacheDir, "cards")
        if (!cardsDir.exists()) cardsDir.mkdirs()

        val file = File(cardsDir, "$fileName.jpg")
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
        }
        return file
    }

    /**
     * Save card image to device's gallery (Downloads/Pictures folder).
     */
    fun saveToGallery(context: Context, cardImagePath: String, productName: String): Boolean {
        return try {
            val sourceFile = File(cardImagePath)
            if (!sourceFile.exists()) return false

            val picturesDir = android.os.Environment.getExternalStoragePublicDirectory(
                android.os.Environment.DIRECTORY_PICTURES
            )
            val kumbaraDir = File(picturesDir, "KumbaraKala")
            if (!kumbaraDir.exists()) kumbaraDir.mkdirs()

            val destFile = File(kumbaraDir, "${productName}_${System.currentTimeMillis()}.jpg")
            sourceFile.copyTo(destFile, overwrite = true)

            // Notify media scanner
            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            mediaScanIntent.data = Uri.fromFile(destFile)
            context.sendBroadcast(mediaScanIntent)

            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Get a content URI via FileProvider for sharing.
     */
    private fun getFileUri(context: Context, file: File): Uri {
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }
}
