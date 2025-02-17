package com.exam.project.presentation.medialist

import com.exam.project.domain.model.Media

sealed interface MediaListAction {
    data class OnSearchQueryChange(val query: String) : MediaListAction
    data class OnMediaClicked(val media: Media) : MediaListAction
    data class OnFocusChange(val isFocused: Boolean) : MediaListAction
    data object OnLoadMore: MediaListAction
}