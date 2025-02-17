package com.exam.project.domain.usecase

import com.exam.project.domain.repository.Repository

class GetSearchHistory(
    private val repository: Repository
) {

    suspend operator fun invoke(): Result<List<String>> {
        return repository.getSearchHistory()
    }

}