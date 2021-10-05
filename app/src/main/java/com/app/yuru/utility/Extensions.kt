package com.app.yuru.utility

import android.content.Context
import android.widget.TextView
import android.widget.Toast
import org.json.JSONArray


fun TextView.trimString(): String {
    return text.trim().toString()
}

fun Context.showToast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

fun JSONArray.concatArray(vararg arrs: JSONArray) {
    for (arr in arrs) {
        for (i in 0 until arr.length()) {
            this.put(arr[i])
        }
    }
}