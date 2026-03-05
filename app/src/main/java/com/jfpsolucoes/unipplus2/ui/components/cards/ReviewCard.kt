package com.jfpsolucoes.unipplus2.ui.components.cards

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jfpsolucoes.unipplus2.R
import com.jfpsolucoes.unipplus2.core.store.UPAppStoreManager
import com.jfpsolucoes.unipplus2.core.utils.extensions.activity
import com.jfpsolucoes.unipplus2.ui.components.spacer.HorizontalSpacer
import com.jfpsolucoes.unipplus2.ui.styles.ButtonColorsPrimaryFixed
import com.jfpsolucoes.unipplus2.ui.styles.defaultCardColors
import com.jfpsolucoes.unipplus2.ui.theme.UNIPPlus2Theme

@Composable
fun ReviewCard(
    modifier: Modifier = Modifier,
    colors: CardColors = defaultCardColors
) {
    val activity = activity
    Card(
        modifier = modifier,
        colors = colors
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(R.string.review_text),
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Button(
                modifier = Modifier
                    .weight(1f)
                ,
                onClick = {
                    UPAppStoreManager.requestReview(activity)
                },
                colors = ButtonColorsPrimaryFixed
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(R.drawable.ic_filled_star_24px),
                    contentDescription = null
                )
                HorizontalSpacer(4.dp)
                Text(
                    text = stringResource(R.string.review_button_text),
                    style = MaterialTheme.typography.titleSmall.copy(
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.weight(1f))
            }

            HorizontalSpacer()

            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    UPAppStoreManager.share(activity)
                },
                colors = ButtonColorsPrimaryFixed
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(R.drawable.ic_outlined_share_24px),
                    contentDescription = null
                )
                HorizontalSpacer(4.dp)
                Text(
                    text = stringResource(R.string.common_share_text),
                    style = MaterialTheme.typography.titleSmall.copy(
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ReviewCardPreview() {
    UNIPPlus2Theme(darkTheme = true) {
        ReviewCard()
    }
}