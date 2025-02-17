package com.exam.project.presentation.mediadetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.exam.project.domain.usecase.GetMedia
import com.exam.project.presentation.route.Route
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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