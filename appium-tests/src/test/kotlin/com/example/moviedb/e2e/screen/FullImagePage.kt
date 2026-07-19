package com.example.moviedb.e2e.screen

import com.example.moviedb.e2e.App
import io.appium.java_client.android.AndroidDriver
import io.qameta.allure.Step
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class FullImagePage(private val driver: AndroidDriver) {

    private val wait = WebDriverWait(driver, Duration.ofSeconds(20))
    private val image = App.id("image")

    @Step("Дождаться экрана полноразмерной картинки")
    fun waitShown(): FullImagePage {
        wait.until(ExpectedConditions.visibilityOfElementLocated(image))
        return this
    }

    fun isImageDisplayed(): Boolean = driver.findElement(image).isDisplayed
}
