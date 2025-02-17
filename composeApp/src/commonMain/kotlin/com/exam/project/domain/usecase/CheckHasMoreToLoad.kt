package com.exam.project.domain.usecase

import com.exam.project.domain.repository.Repository

class CheckHasMoreToLoad(
    private val repository: Repository
) {

    suspend operator fun invoke(query: String): Result<Boolean> {
        return repository.hasNext(query)
    }

}