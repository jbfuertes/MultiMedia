package com.exam.project.data.source.local

import com.exam.project.domain.model.Media


class LocalSourceImpl: LocalSource {

    private var cachedQuery = ""
    private var cachedList = listOf<Media>()
    private var hasNext = true
    private val cachedQueries = mutableListOf<String>()

    override suspend fun saveMediaList(
        mediaList: List<Media>,
        withQuery: String,
        hasNext: Boolean
    ): Result<Unit> {
        cachedList = mediaList
        cachedQuery = withQuery
        this.hasNext = hasNext

        return Result.success(Unit)
    }

    override suspend fun getMediaList(withQuery: String): Result<List<Media>> {
        val list = if (withQuery == cachedQuery) {
            cachedList
        } else emptyList()

        return Result.success(list)
    }

    override suspend fun getCurrentListSize(withQuery: String): Result<Int> {
        val size = if (cachedQuery == withQuery) cachedList.size else 0
        return Result.success(size)
    }

    override suspend fun hasNext(withQuery: String): Result<Boolean> {
        val hasNext = if (cachedQuery == withQuery) hasNext else true
        return Result.success(hasNext)
    }

    override suspend fun saveSearchQuery(withQuery: String): Result<Unit> {
        cachedQueries.add(withQuery)
        return Result.success(Unit)
    }

    override suspend fun getSearchQueries(): Result<List<String>> {
        return Result.success(cachedQueries.toList())
    }

    override suspend fun deleteSearchQuery(withQuery: String): Result<Unit> {
        cachedQueries.remove(withQuery)
        return Result.success(Unit)
    }

    override suspend fun getMedia(id: String): Result<Media> {
        val media = cachedList.find { it.id == id } ?: return Result.failure(NullPointerException("No media found with id: $id"))
        return Result.success(media)
    }
}