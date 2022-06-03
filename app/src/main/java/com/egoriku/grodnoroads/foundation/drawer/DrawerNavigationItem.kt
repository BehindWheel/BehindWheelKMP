package com.egoriku.grodnoroads.foundation.drawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.WSpacer

@Composable
fun DrawerNavigationItem(
    text: String,
    icon: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clickable(onClick = onClick)
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        WSpacer(24.dp)
        Text(
            text = text,
            style = MaterialTheme.typography.body1
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDrawerNavigationItem() {
    DrawerNavigationItem(
        text = "Drawer Item",
        icon = {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Default.Explore,
                contentDescription = null
            )
        }
    ) {}
}