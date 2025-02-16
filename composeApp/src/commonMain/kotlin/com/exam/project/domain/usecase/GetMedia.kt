package com.exam.project.domain.usecase

import com.exam.project.domain.model.Media
import com.exam.project.domain.repository.Repository

class GetMedia(
    private val repository: Repository
) {

    suspend operator fun invoke(id: String): Result<Media> {
        return repository.getMedia(id)
    }

}