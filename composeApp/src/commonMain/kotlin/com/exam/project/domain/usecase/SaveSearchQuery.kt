package com.exam.project.domain.usecase

import com.exam.project.domain.repository.Repository

class SaveSearchQuery(
    private val repository: Repository
) {

    suspend operator fun invoke(query: String): Result<Unit> {
        val searchHistory = repository.getSearchHistory().getOrDefault(emptyList())
        val isMaxHistoryReached = searchHistory.size == MAX_CACHED_SEARCH_HISTORY
        val queryToDelete = if (isMaxHistoryReached) searchHistory.first() else searchHistory.find { it == query }

        if (queryToDelete != null) {
            repository.deleteSearchQuery(queryToDelete)
        }

        return repository.saveSearchQuery(query)
    }

    companion object {
        private const val MAX_CACHED_SEARCH_HISTORY = 5
    }
}