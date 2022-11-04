package com.example.twotwoninezero.common

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Build
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun Fragment.showSuccessToast(msg: String) {
    view?.let { activity?.showSuccessToast(msg) }
}

fun Activity.showSuccessToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
fun Fragment.showMessageToast(msg: String) {
    view?.let { activity?.showMessageToast(msg) }
}

fun Activity.showMessageToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

@RequiresApi(Build.VERSION_CODES.O)
fun dob_date(value:String):String{
    val inputFormatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
    val outputFormatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("dd-MM-yyy", Locale.ENGLISH)
    val date: LocalDate = LocalDate.parse(value, inputFormatter)
    val formattedDate: String = outputFormatter.format(date)
    println(formattedDate) // prints 10-04-2018
    return formattedDate
}

fun getMimeType(context: Context?, uri: Uri?): String? {
    var mimeType: String? = null
    mimeType = if (ContentResolver.SCHEME_CONTENT == uri?.getScheme()) {
        val s = context?.contentResolver?.getType(uri)
        return s

    } else {
        val fileExtension = MimeTypeMap.getFileExtensionFromUrl(
            uri
                .toString()
        )
        MimeTypeMap.getSingleton().getMimeTypeFromExtension(
            fileExtension.toLowerCase()
        )
    }
    return mimeType
}