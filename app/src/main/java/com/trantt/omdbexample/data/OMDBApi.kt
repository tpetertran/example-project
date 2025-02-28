package com.trantt.omdbexample.data

import androidx.annotation.Keep
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

@Keep
interface OMDBApi {
    @GET("/")
    fun searchMovies(
        @Query("apikey") apiKey: String,
        @Query("s") searchQuery: String,
        @Query("page") page: Int
    ): Call<MoviesSearchResponse>
}