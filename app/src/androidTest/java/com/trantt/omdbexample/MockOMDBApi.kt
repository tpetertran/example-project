package com.trantt.omdbexample

import com.trantt.omdbexample.data.Movie
import com.trantt.omdbexample.data.MoviesSearchResponse
import com.trantt.omdbexample.data.OMDBApi
import retrofit2.Call

class MockOMDBApi : OMDBApi {
    override fun searchMovies(apiKey: String, searchQuery: String, page: Int): Call<MoviesSearchResponse> {
        if (searchQuery.length <= 1) {
            return retrofit2.mock.Calls.response(
                MoviesSearchResponse().apply {
                    this.error = "Too many results."
                }
            )
        }
        return retrofit2.mock.Calls.response(
            MoviesSearchResponse().apply {
                this.totalResults = "2"
                this.searchResult = listOf(
                    Movie().apply {
                        this.title = "Movie 1"
                        this.year = "2025"
                    },
                    Movie().apply {
                        this.title = "Movie 2"
                        this.year = "2024"
                    }
                )
            }
        )
    }
}