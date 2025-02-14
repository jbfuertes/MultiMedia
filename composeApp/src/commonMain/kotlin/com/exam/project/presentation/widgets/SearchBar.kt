package com.exam.project.presentation.widgets


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import multimedia.composeapp.generated.resources.Res
import multimedia.composeapp.generated.resources.hint_search
import androidx.compose.material.icons.filled.Search
import org.jetbrains.compose.resources.stringResource
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import multimedia.composeapp.generated.resources.hint_close

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    searchHistory: List<String>,
    onSearchQueryChange: (String) -> Unit,
    onImeSearch: () -> Unit
) {
    val currentFocus = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = modifier
                .minimumInteractiveComponentSize()
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            value = searchQuery,
            singleLine = true,
            placeholder = {
                Text(text = stringResource(Res.string.hint_search))
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = searchQuery.isNotBlank()
                ) {
                    IconButton(
                        onClick = {
                            onSearchQueryChange("")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(Res.string.hint_close)
                        )
                    }
                }
            },
            onValueChange = onSearchQueryChange,
            keyboardActions = KeyboardActions(
                onSearch = {
                    onImeSearch()
                    currentFocus.clearFocus()
                }
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
        )

        if (isFocused) {
            Surface(
                modifier = modifier,
                shadowElevation = 8.dp
            ) {
                LazyColumn {
                    itemsIndexed(searchHistory) { index ,item ->
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .selectable(
                                    selected = true,
                                    onClick = {
                                        onSearchQueryChange(item)
                                        currentFocus.clearFocus()
                                    }
                                ),
                            textAlign = TextAlign.Start,
                            text = item
                        )
                        if (index < searchHistory.size) {
                            HorizontalDivider()
                        }
                    }
                }
            }
        }

    }
}