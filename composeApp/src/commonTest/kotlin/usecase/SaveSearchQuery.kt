package usecase

import com.exam.project.domain.repository.Repository
import com.exam.project.domain.usecase.SaveSearchQuery
import fakes.FAKE_QUERY_SUCCESS
import fakes.FakeRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class SaveSearchQuery {

    private val repository: Repository = FakeRepository()
    private val useCaseToTest = SaveSearchQuery(repository)

    @Test
    fun `should return success when a query is saved`() = runTest {
        val query = FAKE_QUERY_SUCCESS

        val result = useCaseToTest(query)

        assertTrue(result.isSuccess)
    }
}