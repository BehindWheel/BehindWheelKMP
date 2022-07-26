package com.egoriku.grodnoroads.foundation.card

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.ui.theme.GrodnoRoadsTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CheckableCard(
    title: Int,
    selected: Boolean,
    iconId: Int,
    onClick: () -> Unit
) {
    Column(modifier = Modifier.width(120.dp)) {
        Card(
            modifier = Modifier.size(120.dp),
            onClick = onClick,
            shape = RoundedCornerShape(20.dp),
            border = when {
                selected -> BorderStroke(width = 1.5.dp, color = MaterialTheme.colors.secondary)
                else -> null
            }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = iconId),
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            }
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            text = stringResource(id = title),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body2,
            fontWeight = when {
                selected -> FontWeight.Bold
                else -> null
            }
        )
    }
}

@Preview(showBackground = true, locale = "ru")
@Preview(showBackground = true, locale = "be")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun CheckableCardPreview() {
    GrodnoRoadsTheme {
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            CheckableCard(
                title = R.string.map_google_map_style_minimal,
                selected = false,
                iconId = R.drawable.ic_map_style_minimal
            ) {}
            CheckableCard(
                title = R.string.map_google_map_style_detailed,
                selected = true,
                iconId = R.drawable.ic_map_style_detailed
            ) {}
        }
    }
}