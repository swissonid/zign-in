package github.swissonid.zignin.feature.userregistry.domain


import github.swissonid.zignin.feature.userregistry.domain.model.Birthday
import github.swissonid.zignin.feature.userregistry.domain.model.EMail
import github.swissonid.zignin.feature.userregistry.domain.model.Name
import github.swissonid.zignin.feature.userregistry.domain.model.NewUser
import github.swissonid.zignin.feature.userregistry.domain.model.UserId
import github.swissonid.zignin.feature.userregistry.domain.model.toRegisteredUser
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.toLocalDate
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterNewUserUseCaseTest {
    lateinit var userRepositoryMock: UserRepository
    lateinit var registerNewUser: RegisterNewUserUseCase
    private val newUser = NewUser(
        name = Name("John"),
        email = EMail("john.doe@test.com"),
        birthday = Birthday("1990-01-01".toLocalDate()),
    )
    private val registeredUser = newUser.toRegisteredUser(UserId("123"))

    @Before
    fun setUp() {
        userRepositoryMock = mockk(relaxed = true)
        coEvery { userRepositoryMock.registerUser(any()) } returns Result.success(registeredUser)
        registerNewUser = RegisterNewUserUseCase(userRepositoryMock)
    }


    @Test
    fun `registerNewUser - should call registerNewUser`() = runTest {
        registerNewUser(newUser)
        coVerify { userRepositoryMock.registerUser(newUser) }
        confirmVerified(userRepositoryMock)
    }
}