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
import com.egoriku.grodnoroads.appsettings.domain.util.rememberUrlLauncher
import com.egoriku.grodnoroads.compose.resources.Constants.BULLET_SEPARATOR
import com.egoriku.grodnoroads.compose.resources.Constants.PRIVACY_POLICY_LINK
import com.egoriku.grodnoroads.compose.resources.Constants.TERMS_OF_SERVICE_LINK
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.privacy_policy
import com.egoriku.grodnoroads.compose.resources.terms_of_service
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads
import com.egoriku.grodnoroads.foundation.uikit.button.TextButton
import org.jetbrains.compose.resources.stringResource

@Composable
fun PrivacyPolicySection(modifier: Modifier = Modifier) {
    val urlLauncher = rememberUrlLauncher()

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LinkButton(
            openUrl = urlLauncher::openUrl,
            description = stringResource(Res.string.terms_of_service),
            url = TERMS_OF_SERVICE_LINK
        )
        Text(
            text = BULLET_SEPARATOR,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        LinkButton(
            openUrl = urlLauncher::openUrl,
            description = stringResource(Res.string.privacy_policy),
            url = PRIVACY_POLICY_LINK
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

@PreviewGrodnoRoads
@Composable
private fun PrivacyPolicySectionPreview() = GrodnoRoadsM3ThemePreview {
    PrivacyPolicySection()
}
