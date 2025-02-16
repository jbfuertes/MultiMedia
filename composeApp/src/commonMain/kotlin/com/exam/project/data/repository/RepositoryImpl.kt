package com.exam.project.data.repository

import com.exam.project.data.source.local.LocalSource
import com.exam.project.data.source.remote.source.RemoteSource
import com.exam.project.domain.model.Media
import com.exam.project.domain.repository.Repository

class RepositoryImpl(
    private val localSource: LocalSource,
    private val remoteSource: RemoteSource
): Repository {

    override suspend fun getMediaList(query: String, limit: Int): Result<List<Media>> {
        val hasNext = localSource.hasNext(query).getOrDefault(true)
        return if (hasNext.not()) {
            localSource.getMediaList(query)
        } else {
            remoteSource.getMediaList(query, limit)
                .map { list ->
                    list.map { it.toDomain() }.filter { it.kind != null }.take(limit)
                }
        }
    }

    override suspend fun saveMediaList(
        mediaList: List<Media>,
        withQuery: String,
        hasNext: Boolean
    ): Result<Unit> {
        localSource.saveMediaList(mediaList, withQuery, hasNext)
        return Result.success(Unit)
    }

    override suspend fun getCurrentListSize(withQuery: String): Result<Int> {
        return localSource.getCurrentListSize(withQuery)
    }

    override suspend fun hasNext(withQuery: String): Result<Boolean> {
        return localSource.hasNext(withQuery)
    }

    override suspend fun getSearchHistory(): Result<List<String>> {
        return localSource.getSearchQueries()
    }

    override suspend fun deleteSearchQuery(query: String): Result<Unit> {
        return localSource.deleteSearchQuery(query)
    }

    override suspend fun saveSearchQuery(query: String): Result<Unit> {
        return localSource.saveSearchQuery(query)
    }

    override suspend fun getMedia(id: String): Result<Media> {
        return localSource.getMedia(id)
    }
}