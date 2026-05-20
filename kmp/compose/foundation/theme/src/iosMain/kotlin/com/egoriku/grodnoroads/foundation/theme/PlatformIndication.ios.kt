package com.egoriku.grodnoroads.foundation.theme

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import kotlinx.coroutines.launch

@Composable
actual fun platformIndication(): Indication = iosPress()

@Composable
actual fun platformRippleConfiguration(): RippleConfiguration? = null

@Stable
fun iosPress(
    scale: Float = 0.98f,
    alpha: Float = 0.98f,
    animationSpec: AnimationSpec<Float> = tween(120, easing = EaseOut)
): IndicationNodeFactory = IOSPressNodeFactory(scale, alpha, animationSpec)

private class IOSPressNodeFactory(
    private val scale: Float,
    private val alpha: Float,
    private val animationSpec: AnimationSpec<Float>
) : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode = IOSPressIndicationNode(interactionSource, scale, alpha, animationSpec)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is IOSPressNodeFactory) return false

        if (scale != other.scale) return false
        if (alpha != other.alpha) return false
        if (animationSpec != other.animationSpec) return false

        return true
    }

    override fun hashCode(): Int {
        var result = scale.hashCode()
        result = 31 * result + alpha.hashCode()
        result = 31 * result + animationSpec.hashCode()
        return result
    }
}

private class IOSPressIndicationNode(
    private val interactionSource: InteractionSource,
    private val scale: Float,
    private val alpha: Float,
    private val animationSpec: AnimationSpec<Float>
) : Modifier.Node(),
    DelegatableNode,
    DrawModifierNode {
    private val animatedScale = Animatable(1f)
    private val animatedAlpha = Animatable(1f)

    override fun onAttach() {
        super.onAttach()
        startInteractionCollection()
    }

    private fun startInteractionCollection() {
        coroutineScope.launch {
            interactionSource.interactions.collect { interaction ->
                when (interaction) {
                    is PressInteraction.Press -> {
                        launch {
                            animatedScale.animateTo(scale, animationSpec)
                        }
                        launch {
                            animatedAlpha.animateTo(alpha, animationSpec)
                        }
                    }
                    is PressInteraction.Release, is PressInteraction.Cancel -> {
                        launch {
                            animatedScale.animateTo(1f, animationSpec)
                        }
                        launch {
                            animatedAlpha.animateTo(1f, animationSpec)
                        }
                    }
                }
            }
        }
    }

    override fun ContentDrawScope.draw() {
        val currentScale = animatedScale.value
        val currentAlpha = animatedAlpha.value

        scale(scaleX = currentScale, scaleY = currentScale) {
            this@draw.drawContent()
        }

        // Apply alpha by drawing a semi-transparent overlay
        if (currentAlpha < 1f) {
            drawRect(
                color = Color.Black.copy(alpha = 1f - currentAlpha),
                blendMode = BlendMode.Multiply
            )
        }
    }
}
