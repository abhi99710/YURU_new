package com.app.yuru.utility

import android.content.Context
import android.widget.TextView
import android.widget.Toast

fun TextView.trimString(): String {
    return text.trim().toString()
}

fun Context.showToast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}