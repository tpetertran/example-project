package com.trantt.omdbexample.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class MoviesSearchResponse {
    @SerializedName("Search")
    var searchResult: List<Movie>? = null

    @SerializedName("totalResults")
    var totalResults: String? = null

    @SerializedName("Response")
    var response: String? = null

    @SerializedName("Error")
    var error: String? = null
}

@Keep
class Movie {
    @SerializedName("Title")
    var title: String? = null

    @SerializedName("Year")
    var year: String? = null

    @SerializedName("Poster")
    var poster: String? = null
}