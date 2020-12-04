package com.tokopedia.core

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun AppCompatActivity.showToast(message:String?){
    Toast.makeText(this, message ?: "Error tidak diketahui", Toast.LENGTH_LONG).show()
}