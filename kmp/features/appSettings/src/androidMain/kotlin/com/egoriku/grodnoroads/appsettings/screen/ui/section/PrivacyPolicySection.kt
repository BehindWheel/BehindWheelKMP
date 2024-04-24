package com.egoriku.grodnoroads.appsettings.screen.ui.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.egoriku.grodnoroads.appsettings.screen.util.rememberCustomTabIntent
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.button.TextButton
import com.egoriku.grodnoroads.resources.MR
import com.egoriku.grodnoroads.resources.stringResource

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
            description = stringResource(MR.strings.terms_of_service),
            url = stringResource(MR.strings.terms_of_service_link)
        )
        Text(
            text = stringResource(MR.strings.bullet_separator),
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        LinkButton(
            openUrl = { customTabsIntent(it) },
            description = stringResource(MR.strings.privacy_policy),
            url = stringResource(MR.strings.privacy_policy_link)
        )
    }
}

@Composable
private fun LinkButton(
    openUrl: (String) -> Unit,
    description: String,
    url: String
) {
    TextButton(onClick = { openUrl(url) }) {
        Text(
            text = description,
            fontSize = 9.sp
        )
    }
}

@GrodnoRoadsPreview
@Composable
private fun PrivacyPolicySectionPreview() = GrodnoRoadsM3ThemePreview {
    PrivacyPolicySection()
}