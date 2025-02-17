package com.exam.project.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Response<T>(
    val results: T,
    val resultCount: Int
)