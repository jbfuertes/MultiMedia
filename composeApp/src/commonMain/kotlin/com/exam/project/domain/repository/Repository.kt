package com.exam.project.domain.repository

import com.exam.project.domain.model.Media

interface Repository {

    suspend fun getMediaList(
        query: String,
        limit: Int
    ): Result<List<Media>>

    suspend fun saveMediaList(
        mediaList: List<Media>,
        withQuery: String,
        hasNext: Boolean
    ): Result<Unit>

    suspend fun getCurrentListSize(
        withQuery: String
    ): Result<Int>

    suspend fun hasNext(
        withQuery: String
    ): Result<Boolean>

    suspend fun getSearchHistory(): Result<List<String>>

    suspend fun deleteSearchQuery(query: String): Result<Unit>

    suspend fun saveSearchQuery(query: String): Result<Unit>

    suspend fun getMedia(id: String): Result<Media>

}