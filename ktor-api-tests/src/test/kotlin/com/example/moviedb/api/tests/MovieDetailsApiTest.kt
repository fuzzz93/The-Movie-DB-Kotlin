package com.example.moviedb.api.tests

import com.example.moviedb.api.support.MovieDetails
import com.example.moviedb.api.support.TmdbError
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@Epic("TMDb API")
@Feature("Movie details")
class MovieDetailsApiTest : BaseApiTest() {

    @Test
    @Story("Успешный запрос")
    @DisplayName("GET /movie/{id} возвращает 200 и данные фильма")
    fun movieDetailsReturnsMovie() = runBlocking {
        val response = api.movieDetails(movieId = 550)

        assertEquals(HttpStatusCode.OK, response.status)
        val body = response.body<MovieDetails>()
        assertEquals(550, body.id)
        assertTrue(body.title.isNotBlank())
    }

    @Test
    @Story("Ошибка: ресурс не найден")
    @DisplayName("GET /movie/{id} с несуществующим id возвращает 404")
    fun movieDetailsWithUnknownIdReturns404() = runBlocking {
        val response = api.movieDetails(movieId = 0)

        assertEquals(HttpStatusCode.NotFound, response.status)
        val error = response.body<TmdbError>()
        assertEquals(6, error.statusCode)
        assertTrue(!error.success)
    }
}
