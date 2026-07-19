package com.example.moviedb.tests.instrumented

import com.kaspersky.components.alluresupport.withForcedAllureSupport
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase

abstract class BaseUiTest : TestCase(
    kaspressoBuilder = Kaspresso.Builder.withForcedAllureSupport()
)
