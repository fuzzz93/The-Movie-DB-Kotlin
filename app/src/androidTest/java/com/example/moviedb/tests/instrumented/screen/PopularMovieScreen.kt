package com.example.moviedb.tests.instrumented.screen

import android.view.View
import com.example.moviedb.R
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.screen.Screen
import org.hamcrest.Matcher

object PopularMovieScreen : Screen<PopularMovieScreen>() {

    val progress = KView { withId(R.id.progressLoading) }

    val recycler = KRecyclerView(
        builder = { withId(R.id.recyclerView) },
        itemTypeBuilder = { itemType(::MovieItem) }
    )

    class MovieItem(parent: Matcher<View>) : KRecyclerItem<MovieItem>(parent)
}
