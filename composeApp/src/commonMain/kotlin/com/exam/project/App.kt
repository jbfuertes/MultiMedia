package com.exam.project

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.exam.project.presentation.mediadetails.MediaDetailsScreenRoot
import com.exam.project.presentation.mediadetails.MediaDetailsViewModel
import com.exam.project.presentation.medialist.MediaListScreenRoot
import com.exam.project.presentation.medialist.MediaListViewModel
import com.exam.project.presentation.route.Route
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    MaterialTheme {
        NavHost(
            navController = navController,
            startDestination = Route.MediaGraph
        ) {
            navigation<Route.MediaGraph>(
                startDestination = Route.MediaListScreen
            ) {
                composable<Route.MediaListScreen>(
                    popExitTransition = { slideOutHorizontally() },
                    exitTransition = { slideOutHorizontally() }
                ) {

                    val viewModel = koinViewModel<MediaListViewModel>()

                    MediaListScreenRoot(
                        viewModel = viewModel,
                        onMediaClick = {
                            navController.navigate(
                                Route.MediaDetailsScreen(it.id)
                            )
                        }
                    )
                }

                composable<Route.MediaDetailsScreen>(
                    enterTransition = {
                        slideInHorizontally { initialOffset -> initialOffset }
                    },
                    exitTransition = {
                        slideOutHorizontally { initialOffset -> initialOffset }
                    }
                ) {

                    val viewModel = koinViewModel<MediaDetailsViewModel>()

                    MediaDetailsScreenRoot(
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}