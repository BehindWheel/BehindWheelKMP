package com.egoriku.grodnoroads.foundation.designsystem

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsTheme
import com.egoriku.grodnoroads.resources.R

@Composable
fun OutlinedButton(
    @StringRes id: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(8.dp),
        border = ButtonDefaults.outlinedBorder.copy(
            width = 2.dp,
            brush = SolidColor(MaterialTheme.colors.primary.copy(alpha = 0.5f))
        ),
        onClick = onClick
    ) {
        Text(text = stringResource(id))
    }
}

@Preview
@Composable
private fun OutlinedButtonPreview() = GrodnoRoadsTheme {
    Box(modifier = Modifier.size(300.dp, 100.dp)) {
        OutlinedButton(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            id = R.string.app_name,
            onClick = {}
        )
    }
}