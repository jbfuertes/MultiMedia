package com.exam.project.data.source.local

import com.exam.project.domain.model.Media

interface LocalSource {

    suspend fun saveMediaList(
        mediaList: List<Media>,
        withQuery: String,
        hasNext: Boolean
    ): Result<Unit>

    suspend fun getCurrentListSize(withQuery: String): Result<Int>

    suspend fun hasNext(withQuery: String): Result<Boolean>

    suspend fun saveSearchQuery(withQuery: String): Result<Unit>

    suspend fun getSearchQueries(): Result<List<String>>

    suspend fun deleteSearchQuery(withQuery: String): Result<Unit>

    suspend fun getMediaList(withQuery: String): Result<List<Media>>

    suspend fun getMedia(id: String): Result<Media>

}
