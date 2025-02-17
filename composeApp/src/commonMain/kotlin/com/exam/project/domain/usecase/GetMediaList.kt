package com.exam.project.domain.usecase

import com.exam.project.domain.model.Media
import com.exam.project.domain.repository.Repository

class GetMediaList(
    private val repository: Repository
) {

    suspend operator fun invoke(query: String, initialLimit: Int = 50): Result<List<Media>> {
        return repository.let {
            val currentListSize = it.getCurrentListSize(query).getOrDefault(initialLimit)
            val hasNext = it.hasNext(query).getOrDefault(true)

            val limit = if (hasNext) {
                currentListSize + initialLimit
            } else {
                currentListSize
            }

            it.getMediaList(query, limit)
        }
    }

}