package com.app.yuru.utility

import com.github.ajalt.timberkt.Timber

object ThreadInfoLogger {
    fun logThreadInfo(message: String) {
        Timber.d { "logthread : $message  :  Thread name : ${Thread.currentThread().name}" }
    }
}