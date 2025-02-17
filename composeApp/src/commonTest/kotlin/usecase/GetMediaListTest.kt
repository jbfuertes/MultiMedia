package usecase

import com.exam.project.domain.repository.Repository
import com.exam.project.domain.usecase.GetMediaList
import fakes.FAKE_QUERY_0
import fakes.FAKE_QUERY_49
import fakes.FAKE_QUERY_50
import fakes.FAKE_QUERY_FAILED
import fakes.FakeRepository
import fakes.fakeMediaList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetMediaListTest {

    private val repository: Repository = FakeRepository()
    private val useCaseToTest = GetMediaList(repository)

    @Test
    fun `should return list size of 50 on initial call`() = runTest {
        val initialQuery = FAKE_QUERY_0
        val expected = fakeMediaList.take(50)

        val result = useCaseToTest(initialQuery)

        assertTrue(result.isSuccess)
        assertEquals(expected.size, result.getOrThrow().size)
        assertEquals(expected, result.getOrNull())

    }

    @Test
    fun `should return list size of 100 on next page call`() = runTest {
        val query = FAKE_QUERY_50
        val expected = fakeMediaList

        val result = useCaseToTest(query)

        assertTrue(result.isSuccess)
        assertEquals(expected.size, result.getOrThrow().size)
        assertEquals(expected, result.getOrNull())

    }

    @Test
    fun `should return list size of 49 on next page call`() = runTest {
        val query = FAKE_QUERY_49
        val expected = fakeMediaList.take(49)

        val result = useCaseToTest(query)

        assertTrue(result.isSuccess)
        assertEquals(expected.size, result.getOrThrow().size)
        assertEquals(expected, result.getOrNull())

    }

    @Test
    fun `should return throw an exception on failed call`() = runTest {
        val query = FAKE_QUERY_FAILED

        val result = useCaseToTest(query)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is Exception)

    }

}