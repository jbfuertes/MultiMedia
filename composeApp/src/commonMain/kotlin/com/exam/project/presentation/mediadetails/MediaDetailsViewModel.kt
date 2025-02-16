package com.exam.project.presentation.mediadetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.exam.project.domain.usecase.CheckHasMoreToLoad
import com.exam.project.domain.usecase.GetMedia
import com.exam.project.domain.usecase.GetMediaList
import com.exam.project.domain.usecase.GetSearchHistory
import com.exam.project.domain.usecase.SaveMediaList
import com.exam.project.domain.usecase.SaveSearchQuery
import com.exam.project.presentation.medialist.MediaListAction
import com.exam.project.presentation.medialist.MediaListState
import com.exam.project.presentation.route.Route
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MediaDetailsViewModel(
    private val getMedia: GetMedia,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val id = savedStateHandle.toRoute<Route.MediaDetailsScreen>().id

    private val _state = MutableStateFlow(MediaDetailsState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getMedia(id)
                .onSuccess { media ->
                    _state.update {
                        it.copy(media = media)
                    }
                }.onFailure {
                    it.printStackTrace()
                }
        }
    }

}