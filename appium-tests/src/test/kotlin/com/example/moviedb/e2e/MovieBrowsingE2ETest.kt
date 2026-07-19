package com.example.moviedb.e2e

import com.example.moviedb.e2e.screen.PopularListPage
import io.qameta.allure.Description
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@Epic("Black-box E2E (Appium)")
@Feature("Просмотр фильмов")
class MovieBrowsingE2ETest : BaseE2ETest() {

    @BeforeEach
    fun launch() {
        setNetwork(true)
        startApp()
    }

    @Test
    @Story("Экран списка")
    @DisplayName("E2E: список популярных фильмов загружается из сети")
    @Description("Пользователь запускает приложение и видит список фильмов, загруженный с реального TMDb")
    fun popularMoviesListLoadsFromNetwork() {
        val list = PopularListPage(driver).waitLoaded()
        assertTrue(list.items().isNotEmpty(), "Список фильмов пуст")
    }

    @Test
    @Story("Экран деталей")
    @DisplayName("E2E: пользователь открывает детали фильма из списка")
    @Description("Тап по фильму в списке открывает экран деталей с разделом Summary и названием фильма")
    fun movieDetailsOpenFromList() {
        val detail = PopularListPage(driver).waitLoaded()
            .openFirstMovie()
            .waitLoaded()
        assertEquals("Summary", detail.summaryLabel())
        assertTrue(detail.title().isNotBlank(), "Заголовок фильма пуст")
    }

    @Test
    @Story("Экран полноразмерной картинки")
    @DisplayName("E2E: пользователь открывает полноразмерный постер из деталей")
    @Description("Тап по бэкдропу на экране деталей открывает экран полноразмерного изображения")
    fun fullPosterImageOpensFromDetails() {
        val image = PopularListPage(driver).waitLoaded()
            .openFirstMovie()
            .waitLoaded()
            .openBackdrop()
            .waitShown()
        assertTrue(image.isImageDisplayed(), "Полноразмерная картинка не отображается")
    }
}
