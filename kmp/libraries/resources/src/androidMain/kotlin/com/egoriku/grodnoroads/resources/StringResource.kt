package com.egoriku.grodnoroads.resources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import dev.icerock.moko.resources.PluralsResource
import dev.icerock.moko.resources.StringResource

@Composable
@ReadOnlyComposable
fun stringResource(resource: StringResource): String {
    return androidx.compose.ui.res.stringResource(id = resource.resourceId)
}

@Composable
@ReadOnlyComposable
fun stringResource(resource: StringResource, vararg formatArgs: Any): String {
    return androidx.compose.ui.res.stringResource(resource.resourceId, *formatArgs)
}

@Composable
@ReadOnlyComposable
fun pluralStringResource(resource: PluralsResource, count: Int, vararg formatArgs: Any): String {
    return androidx.compose.ui.res.pluralStringResource(resource.resourceId, count, *formatArgs)
}