package com.egoriku.grodnoroads.cityselector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.cityselector.domain.component.CitySelectorComponent
import com.egoriku.grodnoroads.cityselector.domain.component.CitySelectorComponent.CitySelectorPref
import com.egoriku.grodnoroads.cityselector.domain.store.CitySelectorStore.State
import com.egoriku.grodnoroads.cityselector.ui.ChooseCityPage
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.city_selector_done
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PlatformPreviewProvider
import com.egoriku.grodnoroads.foundation.theme.Platform
import com.egoriku.grodnoroads.foundation.uikit.button.PrimaryButton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun CitySelectorScreen(citySelectorComponent: CitySelectorComponent) {
    val state by citySelectorComponent.state.collectAsState()

    Column(
        modifier = Modifier.systemBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ChooseCityPage(
            modifier = Modifier.weight(1f),
            defaultCity = state.defaultCity,
            onModify = citySelectorComponent::modify
        )
        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 16.dp),
            onClick = citySelectorComponent::completeIntro
        ) {
            Text(text = stringResource(Res.string.city_selector_done))
        }
    }
}

@Preview
@Composable
private fun CitySelectorScreenPreview(
    @PreviewParameter(PlatformPreviewProvider::class) platform: Platform
) = GrodnoRoadsM3ThemePreview(platform = platform) {
    CitySelectorScreen(
        citySelectorComponent = object : CitySelectorComponent {
            override val state: StateFlow<State> = MutableStateFlow(State())
            override fun modify(preference: CitySelectorPref) = Unit
            override fun completeIntro() = Unit
        }
    )
}
