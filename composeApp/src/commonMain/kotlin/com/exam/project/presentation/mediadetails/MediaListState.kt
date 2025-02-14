package com.exam.project.presentation.mediadetails

import com.exam.project.domain.Media

data class MediaListState(
    val searchQuery: String = "",
    val searchHistory: List<String> = emptyList(),
    val mediaList: List<Media> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)