package com.egoriku.grodnoroads.screen.main.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.egoriku.grodnoroads.R

sealed class DrawerNavigationScreen(
    @StringRes val resourceId: Int,
    val imageVector: ImageVector
) {
    object Settings : DrawerNavigationScreen(
        resourceId = R.string.settings,
        imageVector = Icons.Filled.Settings
    )
}

@Composable
fun DrawerContent(
    modifier: Modifier = Modifier,
    navigate: (screen: DrawerNavigationScreen) -> Unit
) {
    val navigationItems = listOf(
        DrawerNavigationScreen.Settings
    )

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        navigationItems.forEach {
            DrawerNavigationItem(
                contentPadding = PaddingValues(start = 16.dp),
                text = stringResource(it.resourceId),
                imageVector = it.imageVector,
                onClick = {
                    navigate(it)
                }
            )
        }
    }
}

@Composable
private fun DrawerNavigationItem(
    contentPadding: PaddingValues = PaddingValues(),
    text: String,
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp)
            .padding(paddingValues = contentPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = imageVector,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(24.dp))
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}