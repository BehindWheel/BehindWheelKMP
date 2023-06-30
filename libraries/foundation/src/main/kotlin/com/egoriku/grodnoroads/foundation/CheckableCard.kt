package com.egoriku.grodnoroads.foundation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.resources.R

@OptIn(ExperimentalMaterial3Api::class)
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
                selected -> BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary)
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
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = when {
                selected -> FontWeight.Bold
                else -> null
            }
        )
    }
}

@GrodnoRoadsPreview
@Composable
private fun CheckableCardPreview() = GrodnoRoadsM3ThemePreview {
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