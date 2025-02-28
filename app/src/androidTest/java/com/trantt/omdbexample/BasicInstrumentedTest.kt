package com.trantt.omdbexample

import android.content.Context
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.requestFocus
import androidx.test.core.app.ApplicationProvider
import com.trantt.omdbexample.data.OMDBApi
import com.trantt.omdbexample.ui.section.MainSection
import com.trantt.omdbexample.ui.theme.OMDBExampleTheme
import com.trantt.omdbexample.util.TestTags
import com.trantt.omdbexample.viewmodel.MainViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
class BasicInstrumentedTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Inject
    lateinit var api: OMDBApi

    private lateinit var context: Context
    private lateinit var mainViewModel: MainViewModel

    @Before
    fun init() {
        hiltRule.inject()
        context = ApplicationProvider.getApplicationContext()
        mainViewModel = MainViewModel(api)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun basicSearchCases() {
        composeTestRule.setContent {
            OMDBExampleTheme {
                MainSection(
                    mainViewModel = mainViewModel,
                    context = context
                )
            }
        }
        composeTestRule.onNodeWithTag(TestTags.SEARCH_BAR)
            .requestFocus()
            .performTextInput("m")
        composeTestRule.waitUntilAtLeastOneExists(hasText("Too many results."), 10000)
        composeTestRule.onNodeWithTag(TestTags.SEARCH_BAR)
            .requestFocus()
            .performTextClearance()
        composeTestRule.onNodeWithTag(TestTags.SEARCH_BAR)
            .performTextInput("movie")
        composeTestRule.waitForIdle()
        composeTestRule.waitUntilAtLeastOneExists(hasTestTag(TestTags.MOVIE_ITEM), 10000)
        composeTestRule.onNodeWithTag(TestTags.SEARCH_BAR)
            .requestFocus()
            .performTextClearance()
        composeTestRule.waitUntilDoesNotExist(hasTestTag(TestTags.MOVIE_ITEM), 10000)
    }
}