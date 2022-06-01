package com.egoriku.grodnoroads.foundation.button

import android.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AlertTextButton(
    textResId: Int,
    onClick: () -> Unit
) {
    CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
        TextButton(
            shape = RoundedCornerShape(0.dp),
            contentPadding = PaddingValues(vertical = 8.dp),
            modifier = Modifier.fillMaxWidth(),
            onClick = onClick
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = stringResource(id = textResId),
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, locale = "ru")
@Composable
fun PreviewAlertTextButton() {
    Column {
        AlertTextButton(textResId = R.string.ok) {}
        AlertTextButton(textResId = R.string.cancel) {}
    }
}