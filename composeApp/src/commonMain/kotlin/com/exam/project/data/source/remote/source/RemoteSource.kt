package com.exam.project.data.source.remote.source

import com.exam.project.data.model.MediaDTO

interface RemoteSource {

    suspend fun getMediaList(
        query: String,
        limit: Int
    ): Result<List<MediaDTO>>

}