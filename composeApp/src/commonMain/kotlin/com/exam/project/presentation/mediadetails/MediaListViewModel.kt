package com.exam.project.presentation.mediadetails

import androidx.lifecycle.ViewModel
import com.exam.project.domain.Media
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class MediaListViewModel: ViewModel() {

    private val _state = MutableStateFlow(MediaListState(mediaList = fakeMediaList))
    val state = _state.asStateFlow()

    fun onAction(action: MediaListAction) {
        when (action) {
            is MediaListAction.OnMediaClicked -> TODO()
            is MediaListAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(
                        searchQuery = action.query,
                        searchHistory = fakeSearchHistory,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
private val fakeMediaList = (1 .. 100).map {
    Media(
        Uuid.random().toString(),
        "Title 1",
        "Song",
        "Artist",
        "https://t3.ftcdn.net/jpg/07/73/17/32/360_F_773173210_9qZgsWh2OfGyOVXXrVZ0mstRxckXF9zJ.jpg"
    )
}

private val fakeSearchHistory = listOf("SAMPLE 1", "SAMPLE 2", "SAMPLE 3", "SAMPLE 4", "SAMPLE 5")