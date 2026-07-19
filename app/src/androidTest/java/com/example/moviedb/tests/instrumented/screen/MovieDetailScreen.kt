package com.example.moviedb.tests.instrumented.screen

import com.example.moviedb.R
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KTextView

object MovieDetailScreen : Screen<MovieDetailScreen>() {

    val title = KTextView { withId(R.id.text_title) }
    val releaseDate = KTextView { withId(R.id.text_release_date) }
    val voteAverage = KTextView { withId(R.id.text_vote_avarage) }
    val summaryLabel = KTextView { withId(R.id.text_summary) }
    val overview = KTextView { withId(R.id.text_overview) }
    val backdrop = KImageView { withId(R.id.image_backdrop) }
    val back = KView { withId(R.id.image_back) }
    val favorite = KView { withId(R.id.button_favorite) }
}
