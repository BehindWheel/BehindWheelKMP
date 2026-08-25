package com.egoriku.grodnoroads.settings.debugtools.domain.root

import com.arkivanov.decompose.ComponentContext

fun buildDebugToolsRootComponent(
    componentContext: ComponentContext,
    onOpenUiKit: () -> Unit,
    onOpenDataStoreEdit: () -> Unit,
    onOpenPalette: () -> Unit,
    onOpenAuth: () -> Unit,
    onOpenSpecialEvents: () -> Unit
): DebugToolsRootComponent = DebugToolsRootComponentImpl(
    componentContext = componentContext,
    onOpenUiKit = onOpenUiKit,
    onOpenDataStoreEdit = onOpenDataStoreEdit,
    onOpenPalette = onOpenPalette,
    onOpenAuth = onOpenAuth,
    onOpenSpecialEvents = onOpenSpecialEvents
)

internal class DebugToolsRootComponentImpl(
    componentContext: ComponentContext,
    private val onOpenUiKit: () -> Unit,
    private val onOpenDataStoreEdit: () -> Unit,
    private val onOpenPalette: () -> Unit,
    private val onOpenAuth: () -> Unit,
    private val onOpenSpecialEvents: () -> Unit
) : DebugToolsRootComponent,
    ComponentContext by componentContext {

    override fun onOpenUiKit() = onOpenUiKit.invoke()
    override fun onOpenDataStoreEdit() = onOpenDataStoreEdit.invoke()
    override fun onOpenPalette() = onOpenPalette.invoke()
    override fun onOpenAuth() = onOpenAuth.invoke()
    override fun onOpenSpecialEvents() = onOpenSpecialEvents.invoke()
}
