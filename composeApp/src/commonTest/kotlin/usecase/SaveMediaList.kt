package usecase

import com.exam.project.domain.repository.Repository
import com.exam.project.domain.usecase.SaveMediaList
import fakes.FAKE_QUERY_SUCCESS
import fakes.FakeRepository
import fakes.fakeMediaList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class SaveMediaList {

    private val repository: Repository = FakeRepository()
    private val useCaseToTest = SaveMediaList(repository)

    @Test
    fun `should return success when save media list is called`() = runTest {
        val query = FAKE_QUERY_SUCCESS
        val list = fakeMediaList

        val result = useCaseToTest(list, query)

        assertTrue(result.isSuccess)
    }
}