package com.exam.project.data.model

import com.exam.project.domain.model.Media
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
data class MediaDTO(
    val kind: String? = null,
    val artistName: String,
    val collectionName: String? = null,
    val trackName: String? = null,
    val collectionCensoredName: String? = null,
    val trackViewUrl: String? = null,
    val artworkUrl100: String,
    val description: String? = null,
    val longDescription: String? = null,
    @SerialName("collectionId")
    val id: Long? = null
) {

    @OptIn(ExperimentalUuidApi::class)
    fun toDomain(): Media {
        return Media(
            id = Uuid.random().toString(),
            title = trackName ?: collectionName ?: "",
            kind = kind,
            artist = artistName,
            imageUrl = artworkUrl100,
            description = description ?: longDescription ?: "No Description available"
        )
    }

}