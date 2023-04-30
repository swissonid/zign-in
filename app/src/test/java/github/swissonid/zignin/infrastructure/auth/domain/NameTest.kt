package github.swissonid.zignin.infrastructure.auth.domain

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class NameTest {

    @Test
    fun `checkIfStringIsAValidName - should return NotAValidNameException when name is empty`() {
        val result = checkIfStringIsAValidName("")
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isEqualTo(NotAValidNameException.EmptyName)
    }

    @Test
    fun `checkIfStringIsAValidName - should return NotAValidNameException when name has only blank space`() {
        val result = checkIfStringIsAValidName(" ")
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isEqualTo(NotAValidNameException.OnlyBlankSpace)
    }

    @Test
    fun `checkIfStringIsAValidName - should return the given value when name is valid`() {
        val name = "Swissonid"
        val result = checkIfStringIsAValidName(name)
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(name)
    }

    @Test
    fun `Name_fromString - should return EmptyName when name is empty`() {
        val result = Name.fromString("")
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isEqualTo(NotAValidNameException.EmptyName)
    }

    @Test
    fun `Name_fromString - should return OnlyBlankSpace when name is empty`() {
        val result = Name.fromString("    ")
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isEqualTo(NotAValidNameException.OnlyBlankSpace)
    }

    @Test
    fun `Name_fromString - should return a trimmed name when name is ' swissonid '`() {
        val result = Name.fromString(" swissonid ")
        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrThrow().value).isEqualTo("swissonid")
    }

}