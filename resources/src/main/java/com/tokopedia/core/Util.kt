package com.tokopedia.core

import android.R
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.DisplayMetrics
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.text.NumberFormat
import java.util.*

fun emptyString() = ""

@Suppress("DEPRECATION")
fun String.fromHtml(): Spanned {
    val htmlStr = replace("\n", "<br />")
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(htmlStr, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(htmlStr)
    }
}

fun toBitmap(context: Context, layoutResource: Int): Bitmap {
    return BitmapFactory.decodeResource(context.resources, layoutResource)
}

fun dpToPx(dp: Float, context: Context): Float {
    return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun pxToDp(px: Float, context: Context): Float {
    return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun WebView.loadFile(filePath: String) {
    loadUrl("file:///android_asset/$filePath");
}

fun Context.jsonToString(rawId:Int):String{
    val inputStream: InputStream = resources.openRawResource(rawId)
    val byteArrayOutputStream = ByteArrayOutputStream()

    var ctr: Int
    try {
        ctr = inputStream.read()
        while (ctr != -1) {
            byteArrayOutputStream.write(ctr)
            ctr = inputStream.read()
        }
        inputStream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return byteArrayOutputStream.toString()
}

fun Context.generateBottomSheetDialog(
        layoutId: Int,
        cancelable: Boolean = false,
        style:Int
): BottomSheetDialog {
    return BottomSheetDialog(this, style).apply {
        val view = View.inflate(context, layoutId, null)
        setContentView(view)
        setCancelable(cancelable)
        setCanceledOnTouchOutside(cancelable)
        show()
    }
}

fun TextView.makeStrikeThrough(){
    paintFlags  = Paint.STRIKE_THRU_TEXT_FLAG
}

fun <T>Collection<T>.getMostCommonInList(count:Int):Map<T,Int>{
    return groupBy { it }.mapValues { it.value.size }.toList().sortedByDescending { it.second }.take(count).toMap()
}

fun toRupiahCurrency(value: Int): String {
    return "Rp " + NumberFormat.getNumberInstance(Locale("in")).format(value.toDouble())
}

fun toPercentageFormat(value: Int): String {
    return "$value %"
}