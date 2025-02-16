package com.exam.project.presentation.route

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object MediaGraph: Route

    @Serializable
    data object MediaListScreen: Route

    @Serializable
    data class MediaDetailsScreen(val id: String): Route
}