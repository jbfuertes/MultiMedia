package com.exam.project.presentation.mediadetails

import com.exam.project.domain.Media

sealed interface MediaListAction {
    data class OnSearchQueryChange(val query: String) : MediaListAction
    data class OnMediaClicked(val media: Media) : MediaListAction
}