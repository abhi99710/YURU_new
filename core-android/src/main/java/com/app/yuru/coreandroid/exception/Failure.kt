package com.app.yuru.coreandroid.exception

sealed class Failure {
    object NetworkException : Failure()
    data class ServerError(val message: String?) : Failure()
    data class NOKError(val message: String?) : Failure()
    object LocalDataNotFound : Failure()
}