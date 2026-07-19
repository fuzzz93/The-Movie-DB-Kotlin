package com.example.moviedb.tests.instrumented.screen

import com.example.moviedb.R
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.screen.Screen

object ImageScreen : Screen<ImageScreen>() {

    val image = KImageView { withId(R.id.image) }
    val back = KView { withId(R.id.image_back) }
}
