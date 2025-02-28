package com.trantt.omdbexample.ui.section

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.trantt.omdbexample.ui.component.MovieItemComp
import com.trantt.omdbexample.ui.component.SearchBarComp
import com.trantt.omdbexample.ui.component.text.BodyMediumTextComp
import com.trantt.omdbexample.ui.theme.Dimens
import com.trantt.omdbexample.util.TestTags
import com.trantt.omdbexample.viewmodel.MainViewModel
import com.trantt.omdbexample.viewmodel.MoviesUiState
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun MainSection(
    mainViewModel: MainViewModel,
    context: Context
) {
    val lazyListState = rememberLazyListState()
    val searchQuery by mainViewModel.searchQuery.collectAsState()
    val movies by mainViewModel.movies.collectAsState()

    LaunchedEffect(movies) {
        if (movies is MoviesUiState.IsLoading) {
            lazyListState.scrollToItem(0)
        }
    }

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .distinctUntilChanged()
            .collect { lastVisibleItemIndex ->
                val totalItems = lazyListState.layoutInfo.totalItemsCount
                if (lastVisibleItemIndex != null && lastVisibleItemIndex >= totalItems - 1) {
                    mainViewModel.paginate()
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .systemBarsPadding()
    ) {
        SearchBarComp(
            modifier = Modifier
                .padding(vertical = Dimens.gap24)
                .padding(horizontal = Dimens.gapH),
            searchQuery = searchQuery,
            onValueChange = {
                mainViewModel.onQueryChange(it)
            },
            onQueryClear = {
                mainViewModel.onQueryClear()
            }
        )
        when (val response = movies) {
            is MoviesUiState.Initial -> {
                BodyMediumTextComp(
                    modifier = Modifier
                        .padding(horizontal = Dimens.gapH),
                    text = "Use search to find movies!"
                )
            }

            is MoviesUiState.IsLoading -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is MoviesUiState.NotFound -> {
                BodyMediumTextComp(
                    modifier = Modifier
                        .padding(horizontal = Dimens.gapH),
                    text = response.message
                )
            }

            is MoviesUiState.Success -> {
                LazyColumn(
                    state = lazyListState,
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    items(response.movies) { item ->
                        MovieItemComp(
                            context = context,
                            modifier = Modifier
                                .padding(top = Dimens.gap16)
                                .padding(horizontal = Dimens.gapH)
                                .testTag(TestTags.MOVIE_ITEM),
                            title = item.title.orEmpty(),
                            year = item.year.orEmpty(),
                            picture = item.poster,
                            onClickView = {
                                mainViewModel.onClickView(
                                    context = context,
                                    title = "View ${item.title.orEmpty()}"
                                )
                            }
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(Dimens.gap16))
                    }
                }
            }

            is MoviesUiState.Error -> {
                BodyMediumTextComp(
                    modifier = Modifier
                        .padding(horizontal = Dimens.gapH),
                    text = "Something went wrong"
                )
            }
        }
    }
}
