package com.trantt.omdbexample.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.trantt.omdbexample.ui.component.text.BodyMediumTextComp
import com.trantt.omdbexample.ui.theme.Dimens
import com.trantt.omdbexample.ui.theme.Typography
import com.trantt.omdbexample.util.TestTags

@Composable
fun SearchBarComp(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onValueChange: (String) -> Unit,
    onQueryClear: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp,
        shape = RoundedCornerShape(Dimens.roundedExtraLarge),
        modifier = modifier
    ) {

        TextField(
            value = searchQuery,
            onValueChange = {
                onValueChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(TestTags.SEARCH_BAR),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            maxLines = 1,
            leadingIcon = {
                Icon(
                    imageVector = (Icons.Filled.Search),
                    contentDescription = null
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground
            ),
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            onQueryClear()
                        }
                    ) {
                        Icon(
                            imageVector = (Icons.Filled.Clear),
                            contentDescription = null
                        )
                    }
                }
            },
            textStyle = Typography.bodyMedium,
            placeholder = {
                BodyMediumTextComp(
                    text = "Search for movies"
                )
            }
        )
    }
}