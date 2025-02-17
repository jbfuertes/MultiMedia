package com.exam.project.presentation.medialist

import com.exam.project.domain.model.Media

data class MediaListState(
    val searchQuery: String = "",
    val searchHistory: List<String> = emptyList(),
    val mediaList: List<Media> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val isNextPageLoading: Boolean = false
)