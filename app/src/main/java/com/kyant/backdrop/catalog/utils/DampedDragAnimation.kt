package com.kyant.backdrop.catalog.utils

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.drag
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs

class DampedDragAnimation(
    private val animationScope: CoroutineScope,
    initialValue: Float,
    private val valueRange: ClosedFloatingPointRange<Float>,
    private val visibilityThreshold: Float,
    private val initialScale: Float,
    private val pressedScale: Float,
    private val onDragStarted: () -> Unit,
    private val onDragStopped: () -> Unit,
    private val onDrag: (Float, Float) -> Float
) {
    private val animatable = Animatable(initialValue)

    private val pressAnimation = Animatable(0f)

    var targetValue by mutableFloatStateOf(initialValue)
        private set

    var progress by mutableFloatStateOf(normalize(initialValue))
        private set

    val pressProgress: Float
        get() = pressAnimation.value

    val scaleX: Float
        get() = initialScale + (pressedScale - initialScale) * pressProgress

    val scaleY: Float
        get() = initialScale + (pressedScale - initialScale) * pressProgress

    var velocity by mutableFloatStateOf(0f)
        private set

    private var dragging = false

    val modifier: Modifier
        get() = Modifier
            .pointerInput(this) {
                awaitEachGesture {
                    val down = awaitFirstDown(requireUnconsumed = false)

                    animationScope.launch {
                        pressAnimation.animateTo(
                            1f,
                            animationSpec = spring(
                                stiffness = Spring.StiffnessMediumLow,
                                dampingRatio = Spring.DampingRatioNoBouncy
                            )
                        )
                    }

                    val dragged = drag(down.id) { change ->
                        if (!dragging) {
                            dragging = true
                            onDragStarted()
                        }

                        change.consume()

                        val oldValue = targetValue

                        targetValue = onDrag(
                            oldValue,
                            (change.position - change.previousPosition).x
                        ).coerceIn(valueRange)

                        progress = normalize(targetValue)
                        velocity = targetValue - oldValue
                    }

                    dragging = false

                    if (dragged) {
                        onDragStopped()

                        animationScope.launch {
                            animatable.animateTo(
                                targetValue = targetValue,
                                animationSpec = spring(
                                    stiffness = Spring.StiffnessMediumLow,
                                    dampingRatio = Spring.DampingRatioNoBouncy,
                                    visibilityThreshold = visibilityThreshold
                                )
                            )

                            velocity = 0f
                        }
                    } else {
                        velocity = 0f
                    }

                    animationScope.launch {
                        pressAnimation.animateTo(
                            0f,
                            animationSpec = spring(
                                stiffness = Spring.StiffnessMediumLow,
                                dampingRatio = Spring.DampingRatioNoBouncy
                            )
                        )
                    }
                }
            }
            .graphicsLayer {
                scaleX = this@DampedDragAnimation.scaleX
                scaleY = this@DampedDragAnimation.scaleY
            }

    fun updateValue(value: Float) {
        val clamped = value.coerceIn(valueRange)

        targetValue = clamped
        progress = normalize(clamped)

        if (!dragging) {
            animationScope.launch {
                animatable.snapTo(clamped)
            }
        }
    }

    fun animateToValue(value: Float) {
        val clamped = value.coerceIn(valueRange)

        animationScope.launch {
            animatable.animateTo(
                targetValue = clamped,
                animationSpec = spring(
                    stiffness = Spring.StiffnessMediumLow,
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    visibilityThreshold = visibilityThreshold
                )
            )

            targetValue = clamped
            progress = normalize(clamped)
            velocity = 0f
        }
    }

    private fun normalize(value: Float): Float {
        val range = valueRange.endInclusive - valueRange.start

        if (abs(range) < 0.000001f) {
            return 0f
        }

        return (
            (value - valueRange.start) / range
        ).coerceIn(0f, 1f)
    }
}
