package com.example.moviedb.tests.instrumented

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.moviedb.data.remote.api.MockInterceptor
import com.example.moviedb.tests.instrumented.screen.ImageScreen
import com.example.moviedb.tests.instrumented.screen.MovieDetailScreen
import com.example.moviedb.tests.instrumented.screen.PopularMovieScreen
import com.example.moviedb.ui.screen.main.MainActivity
import io.qameta.allure.kotlin.Description
import io.qameta.allure.kotlin.Epic
import io.qameta.allure.kotlin.Feature
import io.qameta.allure.kotlin.Story
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@Epic("Инструментальные тесты")
@Feature("Навигация по фильмам")
class MovieNavigationTest : BaseUiTest() {

    private var scenario: ActivityScenario<MainActivity>? = null

    @Before
    fun setUp() {
        MockInterceptor.forceHttpError = false
    }

    @After
    fun tearDown() {
        scenario?.close()
    }

    @Test
    @Story("Экран списка")
    @Description("Список популярных фильмов загружается и отображает элементы")
    fun popularListIsDisplayed() = run {
        step("Запускаем приложение") {
            scenario = ActivityScenario.launch(MainActivity::class.java)
        }
        step("Список фильмов отображается и содержит элементы") {
            PopularMovieScreen {
                recycler {
                    isDisplayed()
                    firstChild<PopularMovieScreen.MovieItem> { isDisplayed() }
                }
            }
        }
    }

    @Test
    @Story("Экран деталей")
    @Description("Тап по фильму открывает экран деталей с данными фильма")
    fun openMovieDetail() = run {
        step("Запускаем приложение") {
            scenario = ActivityScenario.launch(MainActivity::class.java)
        }
        step("Открываем детали первого фильма") {
            PopularMovieScreen {
                recycler.childAt<PopularMovieScreen.MovieItem>(0) { click() }
            }
        }
        step("Экран деталей показывает название, обзор и раздел Summary") {
            MovieDetailScreen {
                title {
                    isDisplayed()
                    hasText("Chick Fight")
                }
                summaryLabel { isDisplayed() }
                overview { isDisplayed() }
            }
        }
    }

    @Test
    @Story("Экран полноразмерной картинки")
    @Description("Тап по бэкдропу на экране деталей открывает полноразмерное изображение")
    fun openFullImage() = run {
        step("Запускаем приложение") {
            scenario = ActivityScenario.launch(MainActivity::class.java)
        }
        step("Открываем детали первого фильма") {
            PopularMovieScreen {
                recycler.childAt<PopularMovieScreen.MovieItem>(0) { click() }
            }
        }
        step("Тапаем по бэкдропу") {
            MovieDetailScreen {
                backdrop {
                    isDisplayed()
                    click()
                }
            }
        }
        step("Открылся экран полноразмерной картинки") {
            ImageScreen {
                image { isDisplayed() }
            }
        }
    }
}
