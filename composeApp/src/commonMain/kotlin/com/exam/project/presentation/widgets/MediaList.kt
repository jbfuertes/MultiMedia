package com.exam.project.presentation.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.exam.project.domain.model.Media

@Composable
fun MediaList(
    modifier: Modifier = Modifier,
    mediaList: List<Media>,
    scrollState: LazyListState = rememberLazyListState(),
    isLoading: Boolean = false,
    onMediaClick: (Media) -> Unit,
    onLoadMore: () -> Unit
) {

    LaunchedEffect(scrollState.firstVisibleItemIndex) {
        val lastVisibleItemIndex = scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
        val totalItems = mediaList.size

        if (lastVisibleItemIndex >= totalItems - 20 && isLoading.not()) {
            onLoadMore()
        }
    }

    LazyColumn(
        modifier = modifier,
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(bottom = 40.dp)
    ) {
        itemsIndexed(
            items = mediaList,
            key = { _, item -> item.id }
        ) { index, item ->
            MediaItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                media = item,
                onClick = { onMediaClick(item) }
            )

            if (index == mediaList.size -1) {
                if (isLoading) {
                    CircularProgressIndicator()
                }
            }
        }
    }


}