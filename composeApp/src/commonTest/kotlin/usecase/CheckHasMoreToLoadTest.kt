package usecase

import com.exam.project.domain.repository.Repository
import com.exam.project.domain.usecase.CheckHasMoreToLoad
import fakes.FAKE_QUERY_FAILED
import fakes.FAKE_QUERY_SUCCESS
import fakes.FakeRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CheckHasMoreToLoadTest {

    private val repository: Repository = FakeRepository()
    private val useCaseToTest = CheckHasMoreToLoad(repository)

    @Test
    fun `should return true if there is more to load using valid query`() = runTest {
        val query = FAKE_QUERY_SUCCESS

        val result = useCaseToTest(query)

        assertTrue(result.isSuccess)
        assertTrue(result.getOrThrow())
    }

    @Test
    fun `should return false if there is no more to load using a valid query`() = runTest {
        val query = FAKE_QUERY_FAILED

        val result = useCaseToTest(query)

        assertTrue(result.isSuccess)
        assertFalse(result.getOrThrow())
    }
}