package github.swissonid.zignin.feature.userregistry.domain

import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RemoveCurrentRegisteredUserUseCaseTest {
    lateinit var userRepositoryMock: UserRepository
    lateinit var removeCurrentRegisteredUserUseCase: RemoveCurrentRegisteredUserUseCase

    @Before
    fun setUp() {
        userRepositoryMock = mockk(relaxed = true)
        removeCurrentRegisteredUserUseCase = RemoveCurrentRegisteredUserUseCase(userRepositoryMock)
    }
    
    @Test
    fun `removeCurrentRegisteredUser - should call userRepository`() = runTest {
        removeCurrentRegisteredUserUseCase()
        coVerify { userRepositoryMock.removeCurrentUser() }
        confirmVerified(userRepositoryMock)
    }
}