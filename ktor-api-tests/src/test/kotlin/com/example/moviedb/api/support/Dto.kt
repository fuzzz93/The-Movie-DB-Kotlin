package com.example.moviedb.api.support

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieListResponse(
    val page: Int,
    val results: List<MovieSummary>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int,
)

@Serializable
data class MovieSummary(
    val id: Long,
    val title: String? = null,
)

@Serializable
data class MovieDetails(
    val id: Long,
    val title: String,
    @SerialName("original_title") val originalTitle: String? = null,
)

@Serializable
data class TmdbError(
    @SerialName("status_code") val statusCode: Int,
    @SerialName("status_message") val statusMessage: String,
    val success: Boolean = false,
)
