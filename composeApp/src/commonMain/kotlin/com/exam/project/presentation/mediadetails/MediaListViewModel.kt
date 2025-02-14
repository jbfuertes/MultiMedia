package com.exam.project.presentation.mediadetails

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MediaListViewModel: ViewModel() {

    private val _state = MutableStateFlow(MediaListState())
    val state = _state.asStateFlow()

    fun onAction(action: MediaListAction) {
        when (action) {
            is MediaListAction.OnMediaClicked -> TODO()
            is MediaListAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query, searchHistory = listOf("SAMPLE 1", "SAMPLE 2", "SAMPLE 3", "SAMPLE 4", "SAMPLE 5"))
                }
            }
        }
    }

}