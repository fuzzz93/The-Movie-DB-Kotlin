package com.example.moviedb.api.tests

import com.example.moviedb.api.support.TmdbApi
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

abstract class BaseApiTest {

    protected lateinit var api: TmdbApi

    @BeforeEach
    fun setUp() {
        api = TmdbApi()
    }

    @AfterEach
    fun tearDown() {
        api.close()
    }
}
