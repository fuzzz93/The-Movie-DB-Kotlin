package com.example.moviedb.tests.instrumented

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.moviedb.R
import com.example.moviedb.data.remote.api.MockInterceptor
import com.example.moviedb.tests.instrumented.screen.ErrorDialogScreen
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
@Feature("Обработка ошибок")
class MovieErrorTest : BaseUiTest() {

    private var scenario: ActivityScenario<MainActivity>? = null

    @Before
    fun setUp() {
        MockInterceptor.forceHttpError = true
    }

    @After
    fun tearDown() {
        MockInterceptor.forceHttpError = false
        scenario?.close()
    }

    @Test
    @Story("Диалог ошибки")
    @Description("При ошибке загрузки списка (HTTP 404) показывается диалог ошибки с кнопкой OK")
    fun errorDialogShownOnLoadFailure() = run {
        step("Запускаем приложение (сервер отвечает 404)") {
            scenario = ActivityScenario.launch(MainActivity::class.java)
        }
        step("Показан диалог ошибки с кнопкой OK") {
            ErrorDialogScreen {
                message {
                    isDisplayed()
                    hasText(R.string.unknown_error)
                }
                okButton { isDisplayed() }
            }
        }
        step("Закрываем диалог по кнопке OK") {
            ErrorDialogScreen {
                okButton { click() }
            }
        }
    }
}
