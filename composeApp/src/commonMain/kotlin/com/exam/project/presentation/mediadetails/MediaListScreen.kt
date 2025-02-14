package com.exam.project.presentation.mediadetails
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.exam.project.domain.Media
import org.koin.compose.viewmodel.koinViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exam.project.presentation.widgets.SearchBar

@Composable
fun MediaListScreenRoot(
    viewModel: MediaListViewModel = koinViewModel(),
    onMediaClick: (Media) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MediaListScreen(
        state = state,
        onAction = { action ->
            if (action is MediaListAction.OnMediaClicked) {
                onMediaClick(action.media)
            } else {
                viewModel.onAction(action)
            }
        }
    )

}

@Composable
private fun MediaListScreen(
    state: MediaListState,
    onAction: (MediaListAction) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.fillMaxSize()
            .statusBarsPadding()
    ) {
        Spacer(modifier = Modifier.size(16.dp))

        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            searchQuery = state.searchQuery,
            searchHistory = state.searchHistory,
            onSearchQueryChange = {
                onAction(MediaListAction.OnSearchQueryChange(it))
            },
            onImeSearch = {
                keyboardController?.hide()
            }
        )
    }

}