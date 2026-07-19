package com.example.moviedb.tests.instrumented.screen

import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

object ErrorDialogScreen : Screen<ErrorDialogScreen>() {

    val message = KTextView { withId(android.R.id.message) }
    val okButton = KButton { withId(android.R.id.button1) }
}
