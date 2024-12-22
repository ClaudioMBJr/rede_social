package barbieri.claudio.commons.ext

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

//Save uri as a file
fun Context.getFileFromUri(uri: Uri?): File? {
    if (uri == null) return null

    val file = File(this.filesDir, "image.png")
    val outputStream = FileOutputStream(file)
    val bitmap = this.getBitmapFromUri(uri)

    if (bitmap == null) return null
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    outputStream.close()

    return file
}

//Convert uri to a bitmap
private fun Context.getBitmapFromUri(uri: Uri): Bitmap? {
    return try {
        val inputStream = contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}