package com.egoriku.grodnoroads.settings.root.screen.ui.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsTheme
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.settings.root.screen.ui.util.rememberCustomTabIntent

@Composable
fun PrivacyPolicySection() {
    val customTabsIntent = rememberCustomTabIntent()

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LinkButton(
            openUrl = { customTabsIntent(it) },
            description = stringResource(R.string.terms_of_service),
            url = stringResource(R.string.terms_of_service_link)
        )
        Text(
            text = stringResource(R.string.bullet_separator),
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        LinkButton(
            openUrl = { customTabsIntent(it) },
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

@GrodnoRoadsPreview
@Composable
private fun PrivacyPolicySectionPreview() {
    GrodnoRoadsTheme {
        PrivacyPolicySection()
    }
}