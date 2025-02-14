package com.exam.project.presentation.mediadetails
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.exam.project.domain.Media
import org.koin.compose.viewmodel.koinViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.exam.project.presentation.widgets.MediaList
import com.exam.project.presentation.widgets.SearchBar
import multimedia.composeapp.generated.resources.Res
import multimedia.composeapp.generated.resources.result_empty
import org.jetbrains.compose.resources.stringResource

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
    val lazyListState = rememberLazyListState()

    LaunchedEffect(state.mediaList) {
        lazyListState.animateScrollToItem(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(top = 100.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoading -> CircularProgressIndicator()

                state.errorMessage.isNotBlank() -> {
                    Text(
                        text = state.errorMessage,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                state.mediaList.isEmpty() -> {
                    Text(
                        text = stringResource(Res.string.result_empty),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                else -> {
                    MediaList(
                        modifier = Modifier.fillMaxSize(),
                        mediaList = state.mediaList,
                        scrollState = lazyListState,
                        onMediaClick = {
                            onAction(MediaListAction.OnMediaClicked(it))
                        }
                    )
                }

            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
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