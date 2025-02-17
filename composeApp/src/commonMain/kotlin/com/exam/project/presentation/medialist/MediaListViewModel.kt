package com.exam.project.presentation.medialist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.project.domain.usecase.CheckHasMoreToLoad
import com.exam.project.domain.usecase.GetMediaList
import com.exam.project.domain.usecase.GetSearchHistory
import com.exam.project.domain.usecase.SaveMediaList
import com.exam.project.domain.usecase.SaveSearchQuery
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

class MediaListViewModel(
    private val getMediaList: GetMediaList,
    private val getSearchHistory: GetSearchHistory,
    private val saveMediaList: SaveMediaList,
    private val saveSearchQuery: SaveSearchQuery,
    private val checkHasMoreToLoad: CheckHasMoreToLoad
): ViewModel() {

    private val _state = MutableStateFlow(MediaListState())
    val state = _state.asStateFlow()

    private var job: Job? = null

    init {
        observeSearch()
    }

    fun onAction(action: MediaListAction) {
        when (action) {
            is MediaListAction.OnMediaClicked -> { }
            is MediaListAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }
            is MediaListAction.OnFocusChange -> handleFocusChange(action.isFocused)

            is MediaListAction.OnLoadMore -> handleLoadMedia(_state.value.searchQuery, false)
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeSearch() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(1000)
            .filter { it.isNotBlank() }
            .onEach { query ->
                job?.cancel()
                job = handleLoadMedia(query)
            }
            .launchIn(viewModelScope)

    }

    private fun handleSearchHistoryUpdate(query: String) {
        viewModelScope.launch {
            saveSearchQuery(query)
            getSearchHistory()
                .onSuccess { searchHistory ->
                    _state.update {
                        it.copy(searchHistory = searchHistory.toList())
                    }
                }
        }
    }

    private fun handleLoadMedia(query: String, isFirstLoad: Boolean = true) = viewModelScope.launch {
        if (isFirstLoad.not()) {
            checkHasMoreToLoad(query).onSuccess {
                if (it.not()) return@launch
            }
        }

        _state.update {
            it.copy(
                isLoading = isFirstLoad,
                isNextPageLoading = isFirstLoad.not()
            )
        }

        getMediaList(query)
            .onSuccess { mediaList ->
                saveMediaList(mediaList, query)
                _state.update {
                    it.copy(
                        isLoading = false,
                        isNextPageLoading = false,
                        mediaList = mediaList,
                        errorMessage = ""
                    )
                }
                handleSearchHistoryUpdate(query)
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        isNextPageLoading = false,
                        errorMessage = error.message ?: ""
                    )
                }
            }
    }

    private fun handleFocusChange(isFocused: Boolean) = viewModelScope.launch {
        if (isFocused) {
            getSearchHistory()
                .onSuccess { searchHistory ->
                    _state.update {
                        it.copy(
                            searchHistory = searchHistory
                        )
                    }
                }.onFailure {
                    it.printStackTrace()
                }

        }
    }
}
