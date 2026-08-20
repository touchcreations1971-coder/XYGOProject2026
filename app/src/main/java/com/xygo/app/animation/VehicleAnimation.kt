package com.xygo.app.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class VehicleAnimation {

    val bikeX = Animatable(-700f)
    val carX = Animatable(-900f)
    val loadX = Animatable(900f)

    suspend fun startAnimation() = coroutineScope {

        launch {
            carX.animateTo(
                targetValue = 250f,
                animationSpec = tween(
                    durationMillis = 1800,
                    easing = LinearEasing
                )
            )
        }

        launch {
            loadX.animateTo(
                targetValue = -250f,
                animationSpec = tween(
                    durationMillis = 1800,
                    easing = LinearEasing
                )
            )
        }

        launch {
            bikeX.animateTo(
                targetValue = 0f,
                animationSpec = tween(
                    durationMillis = 2200,
                    easing = LinearEasing
                )
            )
        }
    }
}