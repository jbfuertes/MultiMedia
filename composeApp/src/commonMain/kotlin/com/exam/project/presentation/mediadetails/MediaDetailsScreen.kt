package com.exam.project.presentation.mediadetails
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.exam.project.domain.model.Media
import org.koin.compose.viewmodel.koinViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.exam.project.presentation.medialist.MediaListViewModel
import com.exam.project.presentation.widgets.MediaList
import com.exam.project.presentation.widgets.SearchBar
import multimedia.composeapp.generated.resources.Res
import multimedia.composeapp.generated.resources.result_empty
import org.jetbrains.compose.resources.stringResource

@Composable
fun MediaDetailsScreenRoot(
    viewModel: MediaDetailsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MediaDetailsScreen(
        media = state.media
    )

}

@Composable
private fun MediaDetailsScreen(
    media: Media?
) {
    if (media == null) return

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                var imageResult by remember {
                    mutableStateOf<Result<Painter>?>(null)
                }

                val painter = rememberAsyncImagePainter(
                    model = media.imageUrl,
                    onSuccess = {
                        imageResult = it.painter.intrinsicSize.let { size ->
                            if (size.width > 1 && size.height > 1) {
                                Result.success(it.painter)
                            } else {
                                Result.failure(Exception("Image not valid"))
                            }
                        }
                    },
                    onError = {
                        it.result.throwable.printStackTrace()
                        imageResult = Result.failure(it.result.throwable)
                    }
                )

                if (imageResult == null) {
                    CircularProgressIndicator()
                } else {
                    imageResult
                        ?.onSuccess {
                            Image(
                                modifier = Modifier
                                    .aspectRatio(
                                        ratio = 0.75f,
                                        matchHeightConstraintsFirst = true
                                    ),
                                painter = painter,
                                contentDescription = media.title,
                                contentScale = ContentScale.Crop
                            )
                        }?.onFailure {
                            it.printStackTrace()
                            Icon(
                                modifier = Modifier.size(50.dp)
                                    .background(Color.White),
                                imageVector = Icons.Default.Close,
                                contentDescription = media.title,
                                tint = Color.Red
                            )
                        }
                }
            }

            Text(
                text = media.title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = media.artist,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleSmall
            )

            if (media.kind != null) {
                Text(
                    text = media.kind,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleSmall
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = media.description,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }

}