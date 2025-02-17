package com.exam.project.data.source.remote.source

import com.exam.project.data.model.MediaDTO
import com.exam.project.data.model.Response
import com.exam.project.data.source.remote.util.call
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://itunes.apple.com"

class RemoteSourceImpl(
    private val client: HttpClient
): RemoteSource {

    override suspend fun getMediaList(
        query: String,
        limit: Int
    ): Result<List<MediaDTO>> {
        return call<Response<List<MediaDTO>>> {
            client.get(
                urlString = "$BASE_URL/search"
            ) {
                parameter("term", query)
                parameter("limit", limit)
            }
        }.map { it.results }
    }

}