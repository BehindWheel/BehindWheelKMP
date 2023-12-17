package com.egoriku.grodnoroads.onboarding.domain

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("OnboardingComponent", exact = true)
interface OnboardingComponent {
    fun finishOnboarding()
}