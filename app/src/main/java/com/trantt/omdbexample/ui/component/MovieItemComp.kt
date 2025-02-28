package com.trantt.omdbexample.ui.component

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.trantt.omdbexample.ui.component.text.BodyLargeTextComp
import com.trantt.omdbexample.ui.component.text.BodyMediumTextComp
import com.trantt.omdbexample.ui.theme.Dimens

@Composable
fun MovieItemComp(
    modifier: Modifier = Modifier,
    context: Context,
    title: String,
    year: String,
    picture: String? = null,
    onClickView: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(Dimens.roundedExtraLarge),
        modifier = modifier
            .fillMaxWidth(),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = Dimens.gapH)
                .padding(vertical = Dimens.gap16)
        ) {
            picture?.let {
                AsyncImage(
                    model = ImageRequest
                        .Builder(context = context)
                        .allowHardware(true)
                        .memoryCacheKey(it)
                        .crossfade(true)
                        .data(it)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .heightIn(150.dp)
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = Dimens.gap16)
            ) {
                BodyLargeTextComp(
                    modifier = Modifier,
                    text = title,
                    maxLines = 3
                )
                BodyMediumTextComp(
                    modifier = Modifier
                        .padding(top = Dimens.gap8),
                    text = year
                )
                ActionComp(
                    text = "View",
                    modifier = Modifier
                        .padding(top = Dimens.gap16),
                    onClick = {
                        onClickView()
                    }
                )
            }
        }
    }
}