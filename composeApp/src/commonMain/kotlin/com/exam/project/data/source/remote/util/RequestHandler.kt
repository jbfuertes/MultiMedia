package com.exam.project.data.source.remote.util

import com.exam.project.domain.model.ErrorType
import com.exam.project.domain.model.ServiceException
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

val json = Json {
    ignoreUnknownKeys = true
}

suspend inline fun <reified T> call(request: () -> HttpResponse): Result<T> = try {
    request().toResult<T>()
} catch(e: Exception) {
    val type = when (e) {
        is SocketTimeoutException -> ErrorType.REQUEST_TIME_OUT

        is UnresolvedAddressException -> ErrorType.NO_INTERNET

        else -> ErrorType.UNKNOWN
    }
    Result.failure(
        ServiceException(
            e.message ?: GENERIC_ERROR,
            type
        )
    )
}

suspend inline fun <reified T>HttpResponse.toResult(): Result<T> {
    return when(status.value) {
        in 200 .. 299 -> {
            try {
                val data = withContext(Dispatchers.Default) {
                    json.decodeFromString<T>(bodyAsText())
                }
                withContext(Dispatchers.Main) {
                    Result.success(data)
                }
            } catch (e: NoTransformationFoundException) {
                Result.failure(ServiceException(e.message, ErrorType.SERIALIZATION))
            }
        }

        in 400 .. 499 -> {
            Result.failure(ServiceException(GENERIC_ERROR, ErrorType.BAD_REQUEST))
        }

        in 500 .. 599 -> {
            Result.failure(ServiceException(GENERIC_ERROR, ErrorType.SERVER_DOWN))
        }

        else -> {
            Result.failure(ServiceException(GENERIC_ERROR, ErrorType.UNKNOWN))
        }

    }
}

const val GENERIC_ERROR = "oops. Something went wrong. Please try again"
