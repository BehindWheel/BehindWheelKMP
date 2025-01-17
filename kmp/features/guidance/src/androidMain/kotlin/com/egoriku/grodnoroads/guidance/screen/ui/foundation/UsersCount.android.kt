package com.egoriku.grodnoroads.guidance.screen.ui.foundation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.map_user_count_hint
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads
import com.egoriku.grodnoroads.foundation.theme.isLight
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.compose.Balloon
import com.skydoves.balloon.compose.rememberBalloonBuilder
import com.skydoves.balloon.compose.setBackgroundColor
import com.skydoves.balloon.compose.setTextColor
import org.jetbrains.compose.resources.stringResource

@Composable
actual fun UsersCount(count: Int, modifier: Modifier) {
    val bgColor = MaterialTheme.colorScheme.inversePrimary
    val textColor = MaterialTheme.colorScheme.onSurface
    val isLight = MaterialTheme.colorScheme.isLight

    val builder = rememberBalloonBuilder(isLight) {
        setArrowSize(7)
        setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
        setPaddingTop(4)
        setPaddingBottom(4)
        setPaddingLeft(8)
        setPaddingRight(8)
        setMarginHorizontal(12)
        setCornerRadius(10f)
        setAlpha(0.9f)
        setBackgroundColor(bgColor)
        setTextColor(textColor)
    }

    Balloon(
        modifier = modifier,
        key = isLight,
        builder = builder,
        balloonContent = {
            Text(
                text = stringResource(Res.string.map_user_count_hint),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    ) { balloonWindow ->
        UsersCountBadge(
            onClick = {
                balloonWindow.showAlignTop()
            },
            count = count
        )
    }
}

@PreviewGrodnoRoads
@Composable
private fun UsersCountBadgePreview() = GrodnoRoadsM3ThemePreview {
    Box(modifier = Modifier.padding(16.dp)) {
        UsersCountBadge(count = 10, onClick = {})
    }
}
