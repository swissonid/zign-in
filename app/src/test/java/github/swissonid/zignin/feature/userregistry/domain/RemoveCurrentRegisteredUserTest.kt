package github.swissonid.zignin.feature.userregistry.domain

import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RemoveCurrentRegisteredUserTest {
    lateinit var userRepositoryMock: UserRepository
    lateinit var removeCurrentRegisteredUser: RemoveCurrentRegisteredUser

    @Before
    fun setUp() {
        userRepositoryMock = mockk(relaxed = true)
        removeCurrentRegisteredUser = RemoveCurrentRegisteredUser(userRepositoryMock)
    }


    @Test
    fun `removeCurrentRegisteredUser - should call userRepository`() = runTest {
        removeCurrentRegisteredUser()
        coVerify { userRepositoryMock.removeCurrentUser() }
        confirmVerified(userRepositoryMock)
    }
}