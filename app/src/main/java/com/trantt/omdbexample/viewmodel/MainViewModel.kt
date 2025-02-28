package com.trantt.omdbexample.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trantt.omdbexample.BuildConfig
import com.trantt.omdbexample.data.Movie
import com.trantt.omdbexample.data.MoviesSearchResponse
import com.trantt.omdbexample.data.OMDBApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MoviesUiState {
    data object IsLoading : MoviesUiState()
    data object Initial : MoviesUiState()
    data class Success(val movies: List<Movie>) : MoviesUiState()
    data class NotFound(val message: String) : MoviesUiState()
    data object Error : MoviesUiState()
}

@HiltViewModel
@OptIn(FlowPreview::class)
class MainViewModel @Inject constructor(
    private val api: OMDBApi
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _movies = MutableStateFlow<MoviesUiState>(MoviesUiState.Initial)
    val movies: StateFlow<MoviesUiState> = _movies

    private var currentPage = 1
    private var currentTotal = 0
    private var totalResults = 0

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _searchQuery
                .drop(1)
                .debounce(500L)
                .map { query ->
                    query.trim()
                }
                .collect { query ->
                    currentPage = 1
                    _movies.value = MoviesUiState.IsLoading
                    if (query.isEmpty()) {
                        _movies.value = MoviesUiState.Initial
                    } else {
                        fetchMovies(query)
                    }
                }
        }
    }

    fun onQueryClear() {
        _searchQuery.value = String()
    }

    fun onQueryChange(query: String) {
        _searchQuery.value = query
    }

    private fun fetchMovies(searchQuery: String) {
        try {
            val response = api.searchMovies(
                apiKey = BuildConfig.API_KEY,
                searchQuery = searchQuery,
                page = currentPage
            ).execute()
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    handleFetchMoviesResponse(body)
                    return
                }
            }
            _movies.value = MoviesUiState.Error
        } catch (e: Exception) {
            _movies.value = MoviesUiState.Error
            e.printStackTrace()
        }
    }

    private fun handleFetchMoviesResponse(body: MoviesSearchResponse) {
        body.totalResults?.let {
            totalResults = it.toIntOrNull() ?: 0
        }
        body.searchResult?.let { movies ->
            if (currentPage > 1) { // Paginating
                val currentMovies = (_movies.value as? MoviesUiState.Success)?.movies.orEmpty()
                _movies.value = MoviesUiState.Success(currentMovies + movies)
                currentTotal = currentMovies.size + movies.size
            } else {
                _movies.value = MoviesUiState.Success(movies)
                currentTotal = movies.size
            }
            return
        }
        body.error?.let {
            _movies.value = MoviesUiState.NotFound(it)
            return
        }
    }

    fun paginate() {
        if (currentTotal < totalResults) {
            currentPage++
            viewModelScope.launch(Dispatchers.IO) {
                fetchMovies(searchQuery = searchQuery.value.trim())
            }
        }
    }

    fun onClickView(context: Context, title: String) {
        Toast.makeText(context, title, Toast.LENGTH_SHORT).show()
    }
}