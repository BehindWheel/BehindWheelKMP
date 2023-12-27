package com.egoriku.grodnoroads.guidance.domain.store.quickactions.model

import com.egoriku.grodnoroads.guidance.domain.store.quickactions.model.QuickActionsPref.AppTheme

data class QuickActionsState(
    val appTheme: AppTheme = AppTheme()
)