package com.trantt.omdbexample.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.trantt.omdbexample.ui.component.text.BodyMediumTextComp
import com.trantt.omdbexample.ui.theme.Dimens

@Composable
fun ActionComp(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {},
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = RoundedCornerShape(CornerSize(Int.MAX_VALUE.dp)),
            color = MaterialTheme.colorScheme.primary,
            tonalElevation = 0.dp
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        onClick()
                    }
                    .padding(vertical = Dimens.gap8, horizontal = Dimens.gap12)
            ) {
                BodyMediumTextComp(
                    text = text,
                    color = MaterialTheme.colorScheme.onPrimary,
                    maxLines = 1
                )
            }
        }
    }
}