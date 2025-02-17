package fakes

import com.exam.project.domain.model.Media
import com.exam.project.domain.repository.Repository

class FakeRepository: Repository {

    override suspend fun getMediaList(query: String, limit: Int): Result<List<Media>> {
        return when (query) {
            FAKE_QUERY_0 -> Result.success(fakeMediaList.take(50))
            FAKE_QUERY_50, FAKE_QUERY_SUCCESS -> Result.success(fakeMediaList)
            FAKE_QUERY_49 -> Result.success(fakeMediaList.take(49))
            else -> Result.failure(Exception())
        }
    }

    override suspend fun saveMediaList(
        mediaList: List<Media>,
        withQuery: String,
        hasNext: Boolean
    ): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun getCurrentListSize(withQuery: String): Result<Int> {
        return Result.success(
            when (withQuery) {
                FAKE_QUERY_0 -> 0
                FAKE_QUERY_50 -> 50
                else -> 49
            }
        )
    }

    override suspend fun hasNext(withQuery: String): Result<Boolean> {
        return Result.success(withQuery == FAKE_QUERY_SUCCESS)
    }

    override suspend fun getSearchHistory(): Result<List<String>> {
        return Result.success(fakeSearchHistory)
    }

    override suspend fun deleteSearchQuery(query: String): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun saveSearchQuery(query: String): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun getMedia(id: String): Result<Media> {
        return if (id == FAKE_ID_SUCCESS) {
            Result.success(fakeMediaList[0])
        } else {
            Result.failure(Exception())
        }
    }
}



internal const val FAKE_QUERY_FAILED = "failed_query"
internal const val FAKE_QUERY_SUCCESS = "success_query"
internal const val FAKE_QUERY_0 = "0"
internal const val FAKE_QUERY_50 = "50"
internal const val FAKE_QUERY_49 = "49"

internal const val FAKE_ID_FAILED = "failed_id"
internal const val FAKE_ID_SUCCESS = "1"

internal val fakeMediaList = (1 .. 100).map {
    Media(
        it.toString(),
        "Title 1",
        "Song",
        "Artist",
        "https://t3.ftcdn.net/jpg/07/73/17/32/360_F_773173210_9qZgsWh2OfGyOVXXrVZ0mstRxckXF9zJ.jpg",
        "Fake Description"
    )
}

internal val fakeSearchHistory = listOf("SAMPLE 1", "SAMPLE 2", "SAMPLE 3", "SAMPLE 4", "SAMPLE 5")