package com.example.moviedb.e2e

import com.example.moviedb.e2e.screen.ErrorDialogPage
import io.qameta.allure.Description
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@Epic("Black-box E2E (Appium)")
@Feature("Обработка ошибок сети")
class OfflineErrorE2ETest : BaseE2ETest() {

    @Test
    @Story("Offline")
    @DisplayName("E2E: без сети приложение показывает диалог No Internet Connection")
    @Description("Сеть отключается снаружи через adb (svc wifi/data disable) до запуска приложения; при неудачной загрузке списка появляется диалог отсутствия соединения с кнопкой OK")
    fun offlineShowsNoInternetDialog() {
        setNetwork(false)
        startApp()
        val dialog = ErrorDialogPage(driver).waitShown()
        assertTrue(
            dialog.messageText().contains("Internet", ignoreCase = true),
            "Неожиданный текст ошибки: ${dialog.messageText()}"
        )
        assertTrue(dialog.isOkDisplayed(), "Нет кнопки OK")
        dialog.tapOk()
    }
}
