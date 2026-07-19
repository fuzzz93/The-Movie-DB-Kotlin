package com.example.moviedb.e2e

import io.appium.java_client.AppiumBy
import org.openqa.selenium.By

object App {
    const val PACKAGE = "com.example.moviedb.dev"
    const val MAIN_ACTIVITY = "com.example.moviedb.ui.screen.main.MainActivity"

    fun id(name: String): By = AppiumBy.id("$PACKAGE:id/$name")
}
