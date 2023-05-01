package github.swissonid.zignin.featue.userregistry.data

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import github.swissonid.zignin.feature.userregistry.data.SharedPreferencesUserRepository
import github.swissonid.zignin.feature.userregistry.domain.UserRegistryException
import github.swissonid.zignin.feature.userregistry.domain.model.Birthday
import github.swissonid.zignin.feature.userregistry.domain.model.EMail
import github.swissonid.zignin.feature.userregistry.domain.model.Name
import github.swissonid.zignin.feature.userregistry.domain.model.NewUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.toLocalDate
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class) // this is needed for runTest
@RunWith(AndroidJUnit4::class)
class SharedPreferencesUserRepositoryTest {

    private val testContext = InstrumentationRegistry.getInstrumentation().context
    private lateinit var userRepository: SharedPreferencesUserRepository
    private val newUser = NewUser(
        name = Name("John"),
        email = EMail("john.doe@test.com"),
        birthday = Birthday("1990-01-01".toLocalDate()),
    )

    @Before
    fun setUp() {
        val sharedPref = testContext.getSharedPreferences(
            "test",
            Context.MODE_PRIVATE
        )
        with(sharedPref.edit()) {
            clear()
            commit()
        }
        userRepository = SharedPreferencesUserRepository(sharedPref)
    }

    @Test
    fun setupTest() {
        assertThat(userRepository).isNotNull()
    }


    @Test
    fun currentUser_shouldReturnNoUserIsRegistered() = runTest {
        val result = userRepository.currentUser()
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isEqualTo(UserRegistryException.NoUserIsRegistered)
    }

    @Test
    fun registerUser_shouldRegisterUser() = runTest {

        val result = userRepository.registerUser(newUser)
        assertThat(result.isSuccess).isTrue()
        val registeredUser = result.getOrThrow()
        assertThat(registeredUser.id).isNotNull()
        assertThat(registeredUser.name).isEqualTo(newUser.name)
        assertThat(registeredUser.email).isEqualTo(newUser.email)
        assertThat(registeredUser.birthday).isEqualTo(newUser.birthday)
    }

    @Test
    fun currentUser_shouldReturnRegisteredUser() = runTest {
        val registeredUser = userRepository.registerUser(newUser).getOrThrow()
        val result = userRepository.currentUser()
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(registeredUser)
    }

    @Test
    fun currentUser_shouldReturnNoUserIsRegistered_afterUnregister() = runTest {
        userRepository.registerUser(newUser).getOrThrow()
        userRepository.removeCurrentUser()
        val result = userRepository.currentUser()
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isEqualTo(UserRegistryException.NoUserIsRegistered)
    }

}