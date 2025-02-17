package usecase

import com.exam.project.domain.repository.Repository
import com.exam.project.domain.usecase.GetMedia
import fakes.FAKE_ID_FAILED
import fakes.FAKE_ID_SUCCESS
import fakes.FakeRepository
import fakes.fakeMediaList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetMediaTest {

    private val repository: Repository = FakeRepository()
    private val useCaseToTest = GetMedia(repository)

    @Test
    fun `should return media when id is valid`() = runTest {
        val id = FAKE_ID_SUCCESS
        val expected = fakeMediaList[0]

        val result = useCaseToTest(id)

        assertTrue(result.isSuccess)
        assertEquals(expected, result.getOrNull())

    }

    @Test
    fun `should return result failure when id is invalid`() = runTest {
        val id = FAKE_ID_FAILED

        val result = useCaseToTest(id)

        assertTrue(result.isFailure)
        assertEquals(null, result.getOrNull())
        assertTrue(result.exceptionOrNull() is Exception)

    }

}