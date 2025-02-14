package com.exam.project

import androidx.compose.runtime.*
import com.exam.project.presentation.mediadetails.MediaListScreenRoot
import com.exam.project.presentation.mediadetails.MediaListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MediaListScreenRoot(
        viewModel = remember { MediaListViewModel() },
        onMediaClick = {

        }
    )
}