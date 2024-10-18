package com.egoriku.grodnoroads.eventreporting.screen.ui.util

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity
import kotlin.jvm.JvmName

@OptIn(ExperimentalFoundationApi::class)
internal fun <T> AnchoredDraggableState<T>.preUpPostDownNestedScrollConnection() =
    object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            val delta = available.toFloat()
            return if (delta < 0 && source == NestedScrollSource.UserInput) {
                dispatchRawDelta(delta).toOffset()
            } else {
                Offset.Zero
            }
        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource
        ): Offset {
            return if (source == NestedScrollSource.UserInput) {
                dispatchRawDelta(available.toFloat()).toOffset()
            } else {
                Offset.Zero
            }
        }

        override suspend fun onPreFling(available: Velocity): Velocity {
            val toFling = available.toFloat()
            return if (toFling < 0 && offset > anchors.minAnchor()) {
                settle(toFling)
                // since we go to the anchor with tween settling, consume all for the best UX
                available
            } else {
                Velocity.Zero
            }
        }

        override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
            val toFling = available.toFloat()
            return if (toFling > 0) {
                settle(toFling)
                available
            } else {
                Velocity.Zero
            }
        }

        private fun Float.toOffset(): Offset = Offset(0f, this)

        @JvmName("velocityFloat")
        private fun Velocity.toFloat() = this.y
        private fun Offset.toFloat(): Float = this.y
    }
