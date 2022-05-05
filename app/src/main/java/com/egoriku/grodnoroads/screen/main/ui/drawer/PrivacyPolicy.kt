package com.egoriku.grodnoroads.screen.main.ui.drawer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.egoriku.grodnoroads.R

@Composable
fun PrivacyPolicy(openUrl: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LinkButton(
            openUrl = openUrl,
            description = stringResource(R.string.terms_of_service),
            url = stringResource(R.string.terms_of_service_link)
        )
        Text(
            text = stringResource(R.string.bullet_separator),
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        LinkButton(
            openUrl = openUrl,
            description = stringResource(R.string.privacy_policy),
            url = stringResource(R.string.privacy_policy_link)
        )
    }
}

@Composable
private fun LinkButton(
    openUrl: (String) -> Unit,
    description: String,
    url: String
) {
    TextButton(
        onClick = { openUrl(url) },
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = description,
            fontSize = 9.sp
        )
    }
}

@Preview(locale = "en")
@Preview(locale = "ru")
@Composable
fun PrivacyPolicyPreview() {
    Surface {
        PrivacyPolicy {}
    }
}