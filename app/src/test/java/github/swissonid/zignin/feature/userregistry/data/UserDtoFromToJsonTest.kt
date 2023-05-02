package github.swissonid.zignin.feature.userregistry.data

import com.google.common.truth.Truth.assertThat
import github.swissonid.zignin.feature.userregistry.domain.model.Birthday
import github.swissonid.zignin.feature.userregistry.domain.model.EMail
import github.swissonid.zignin.feature.userregistry.domain.model.Name
import github.swissonid.zignin.feature.userregistry.domain.model.NewUser
import github.swissonid.zignin.feature.userregistry.domain.model.RegisteredUser
import github.swissonid.zignin.feature.userregistry.domain.model.User
import github.swissonid.zignin.feature.userregistry.domain.model.UserId
import kotlinx.datetime.toLocalDate
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Suite

private val newUserJson = """
        {
            "name": "John",
            "email": "john.doe@test.com",
            "birthday": "1990-01-01"
        }
    """.trimIndent()
private val expectedNewUserDto = UserDto(
    name = "John",
    email = "john.doe@test.com",
    birthday = "1990-01-01"
)

private val newUser = NewUser(
    name = Name("John"),
    email = EMail("john.doe@test.com"),
    birthday = Birthday("1990-01-01".toLocalDate())
)


private val registeredUserJson = """
        {
            "id": "1234567890",
            "name": "John",
            "email": "john.doe@test.com",
            "birthday": "1990-01-01"
        }
    """.trimIndent()

private val expectedRegisteredUserDto = UserDto(
    id = "1234567890",
    name = "John",
    email = "john.doe@test.com",
    birthday = "1990-01-01"
)

private val registeredUser = RegisteredUser(
    id = UserId("1234567890"),
    name = Name("John"),
    email = EMail("john.doe@test.com"),
    birthday = Birthday("1990-01-01".toLocalDate())
)

@RunWith(Suite::class)
@Suite.SuiteClasses(
    UserDtoFromToJsonTest::class,
    UserDtoFromToDomainTest::class,
)
class UserDtoTes

@RunWith(Parameterized::class)
class UserDtoFromToJsonTest(private val jsonString: String, private val userDto: UserDto) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0}")
        fun data(): Collection<Array<Any>> = listOf(
            arrayOf(newUserJson, expectedNewUserDto),
            arrayOf(registeredUserJson, expectedRegisteredUserDto),
        )
    }

    @Test
    fun `should deserialize json to UserDto`() {
        val result = UserDto.fromJson(jsonString)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow()).isEqualTo(userDto)
    }

    @Test
    fun `should serialize UserDto to json`() {
        val result = userDto.toJson()
        // remove all whitespaces and newlines
        val expected = jsonString.replace("\\s+".toRegex(), "")
        assertThat(result).isEqualTo(expected)
    }
}

@RunWith(value = Parameterized::class)
class UserDtoFromToDomainTest(private val domain: User, private val userDto: UserDto) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0}")
        fun data(): Collection<Array<Any>> = listOf(
            arrayOf(newUser, expectedNewUserDto),
            arrayOf(registeredUser, expectedRegisteredUserDto),
        )
    }

    @Test
    fun `should convert a User to UserDto`() {
        val result = UserDto.fromDomain(domain)
        assertThat(result).isEqualTo(userDto)
    }

    @Test
    fun `should convert a UserDto to User`() {
        val result = userDto.toDomain()
        assertThat(result.isSuccess).isTrue()

        assertThat(result.getOrThrow()).isEqualTo(domain)
    }
}