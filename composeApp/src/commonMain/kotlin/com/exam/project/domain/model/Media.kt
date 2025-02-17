package com.exam.project.domain.model

data class Media(
    val id: String,
    val title: String,
    val kind: String?,
    val artist: String,
    val imageUrl: String,
    val description: String
)
