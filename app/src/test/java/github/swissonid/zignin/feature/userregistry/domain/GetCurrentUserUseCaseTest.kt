package github.swissonid.zignin.feature.userregistry.domain

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetCurrentUserUseCaseTest {
    lateinit var userRepositoryMock: UserRepository
    lateinit var getCurrentUserUseCase: GetCurrentUserUseCase

    @Before
    fun setUp() {
        userRepositoryMock = mockk(relaxed = true)
        getCurrentUserUseCase = GetCurrentUserUseCase(userRepositoryMock)
    }


    @Test
    fun `getCurrentUserUseCase - should call userRepository`() = runTest {
        getCurrentUserUseCase()
        coVerify { userRepositoryMock.currentUser() }
    }
}