package com.exam.project.presentation.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.exam.project.domain.Media

@Composable
fun MediaItem(
    modifier: Modifier = Modifier,
    media: Media,
    onClick: () -> Unit
) {

    Surface(
        modifier = modifier
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        color = Color.Green.copy(alpha = 0.3f),
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(100.dp),
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
                                println("HERE: ON SUCCESS")
                                Result.failure(Exception("Image not valid"))
                            }
                        }
                    },
                    onError = {
                        println("HERE: ON ERROR")
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

            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = media.title,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = media.artist,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = media.kind,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

    }

}