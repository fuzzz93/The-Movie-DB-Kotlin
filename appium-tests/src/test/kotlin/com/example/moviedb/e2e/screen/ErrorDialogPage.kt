package com.example.moviedb.e2e.screen

import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver
import io.qameta.allure.Step
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class ErrorDialogPage(private val driver: AndroidDriver) {

    private val wait = WebDriverWait(driver, Duration.ofSeconds(30))
    private val message = AppiumBy.id("android:id/message")
    private val okButton = AppiumBy.id("android:id/button1")

    @Step("Дождаться диалога ошибки")
    fun waitShown(): ErrorDialogPage {
        wait.until(ExpectedConditions.visibilityOfElementLocated(message))
        return this
    }

    @Step("Прочитать текст ошибки")
    fun messageText(): String = driver.findElement(message).text

    fun isOkDisplayed(): Boolean = driver.findElement(okButton).isDisplayed

    @Step("Закрыть диалог по OK")
    fun tapOk() {
        driver.findElement(okButton).click()
    }
}
