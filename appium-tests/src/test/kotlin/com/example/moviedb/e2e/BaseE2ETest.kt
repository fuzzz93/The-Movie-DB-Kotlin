package com.example.moviedb.e2e

import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.options.UiAutomator2Options
import io.qameta.allure.Attachment
import org.junit.jupiter.api.AfterEach
import org.openqa.selenium.OutputType
import java.net.URL
import java.time.Duration

abstract class BaseE2ETest {

    protected lateinit var driver: AndroidDriver

    protected fun startApp() {
        adb("shell", "am", "force-stop", App.PACKAGE)
        val options = UiAutomator2Options()
            .setPlatformName("Android")
            .setAutomationName("UiAutomator2")
            .setDeviceName(DEVICE)
            .setAppPackage(App.PACKAGE)
            .setAppActivity(App.MAIN_ACTIVITY)
            .setAppWaitActivity(App.MAIN_ACTIVITY)
            .setNewCommandTimeout(Duration.ofSeconds(120))
            .setNoReset(true)
            .amend("appium:forceAppLaunch", true)
        driver = AndroidDriver(URL(SERVER), options)
    }

    protected fun setNetwork(enabled: Boolean) {
        val mode = if (enabled) "enable" else "disable"
        adb("shell", "svc", "wifi", mode)
        adb("shell", "svc", "data", mode)
    }

    @Attachment(value = "screenshot", type = "image/png")
    protected fun screenshot(): ByteArray = driver.getScreenshotAs(OutputType.BYTES)

    @AfterEach
    fun tearDown() {
        try {
            if (::driver.isInitialized) {
                runCatching { screenshot() }
                driver.quit()
            }
        } finally {
            setNetwork(true)
        }
    }

    companion object {
        private const val SERVER = "http://127.0.0.1:4723"
        private const val DEVICE = "emulator-5554"
        private val ADB = (System.getenv("ANDROID_HOME")
            ?: "${System.getProperty("user.home")}/Library/Android/sdk") + "/platform-tools/adb"

        fun adb(vararg args: String) {
            ProcessBuilder(listOf(ADB, "-s", DEVICE) + args)
                .redirectErrorStream(true)
                .start()
                .waitFor()
        }
    }
}
