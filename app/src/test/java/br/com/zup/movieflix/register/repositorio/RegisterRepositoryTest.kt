package br.com.zup.movieflix.register.repositorio

import br.com.zup.movieflix.register.model.RegisterModel
import org.junit.Assert.assertEquals
import org.junit.Test

internal class RegisterRepositoryTest {

    @Test
    fun registerUser_noUser_ReturnZeroUser() {
        val registerRepository = RegisterRepository()
        // Arrange
        val user = RegisterModel(
            "Felipe",
            "felipe.dearaujo@zup.com.br",
            "melhorProfessorDoMundo"
        )
        // ACT
        val result = registerRepository.registerUser(user)

        // Assert
        assertEquals(result.email, "felipe.dearaujo@zup.com.br")
        assertEquals(result.password, "melhorProfessorDoMundo")
        assertEquals(result.userName, "Felipe")
    }
}