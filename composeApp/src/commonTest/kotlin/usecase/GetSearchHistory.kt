package usecase

import com.exam.project.domain.repository.Repository
import com.exam.project.domain.usecase.GetSearchHistory
import fakes.FakeRepository
import fakes.fakeSearchHistory
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetSearchHistory {

    private val repository: Repository = FakeRepository()
    private val useCaseToTest = GetSearchHistory(repository)

    @Test
    fun `should return search history when called`() = runTest {
        val expected = fakeSearchHistory

        val result = useCaseToTest()

        assertTrue(result.isSuccess)
        assertEquals(expected.size, result.getOrThrow().size)
        assertEquals(expected, result.getOrNull())

    }

}