package com.exam.project.domain.model

open class ServiceException(
    val errorMessage: String,
    val type: ErrorType
): RuntimeException(errorMessage)

enum class ErrorType {
    REQUEST_TIME_OUT,
    NO_INTERNET,
    UNKNOWN,
    SERVER_DOWN,
    SERIALIZATION,
    BAD_REQUEST
}