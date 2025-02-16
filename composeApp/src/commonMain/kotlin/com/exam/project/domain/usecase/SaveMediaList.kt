package com.exam.project.domain.usecase

import com.exam.project.domain.model.Media
import com.exam.project.domain.repository.Repository

class SaveMediaList(
    private val repository: Repository
) {

    suspend operator fun invoke(list: List<Media>, withQuery: String, initialLimit: Int = 50): Result<Unit> {
        val limit = repository.getCurrentListSize(withQuery).getOrDefault(0) + initialLimit
        val hasNext = list.size >= limit
        return repository.saveMediaList(list, withQuery, hasNext)
    }

}