package com.example.moviedb.e2e.screen

import com.example.moviedb.e2e.App
import io.appium.java_client.AppiumBy
import io.appium.java_client.android.AndroidDriver
import io.qameta.allure.Step
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class PopularListPage(private val driver: AndroidDriver) {

    private val wait = WebDriverWait(driver, Duration.ofSeconds(30))
    private val recycler = App.id("recyclerView")

    @Step("Дождаться загрузки списка фильмов")
    fun waitLoaded(): PopularListPage {
        wait.until(ExpectedConditions.visibilityOfElementLocated(recycler))
        wait.until { items().isNotEmpty() }
        return this
    }

    fun items(): List<WebElement> =
        driver.findElement(recycler).findElements(AppiumBy.xpath("./*"))

    @Step("Открыть детали первого фильма")
    fun openFirstMovie(): MovieDetailsPage {
        val firstItem = wait.until(
            ExpectedConditions.elementToBeClickable(
                AppiumBy.androidUIAutomator(
                    "new UiSelector().resourceId(\"${App.PACKAGE}:id/recyclerView\")" +
                        ".childSelector(new UiSelector().clickable(true))"
                )
            )
        )
        firstItem.click()
        return MovieDetailsPage(driver)
    }
}
