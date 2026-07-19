package com.example.moviedb.e2e.screen

import com.example.moviedb.e2e.App
import io.appium.java_client.android.AndroidDriver
import io.qameta.allure.Step
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class MovieDetailsPage(private val driver: AndroidDriver) {

    private val wait = WebDriverWait(driver, Duration.ofSeconds(20))
    private val title = App.id("text_title")
    private val summary = App.id("text_summary")
    private val backdrop = App.id("image_backdrop")

    @Step("Дождаться экрана деталей фильма")
    fun waitLoaded(): MovieDetailsPage {
        wait.until(ExpectedConditions.visibilityOfElementLocated(title))
        wait.until(ExpectedConditions.presenceOfElementLocated(summary))
        return this
    }

    @Step("Прочитать заголовок фильма")
    fun title(): String = driver.findElement(title).text

    @Step("Прочитать подпись раздела Summary")
    fun summaryLabel(): String = driver.findElement(summary).text

    @Step("Открыть полноразмерную картинку по бэкдропу")
    fun openBackdrop(): FullImagePage {
        wait.until(ExpectedConditions.elementToBeClickable(backdrop)).click()
        return FullImagePage(driver)
    }
}
