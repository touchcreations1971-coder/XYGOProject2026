package com.xygo.app.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import kotlinx.coroutines.delay

class Motion {

    val bikeX = Animatable(-1200f)
    val bikeY = Animatable(350f)
    val bikeScale = Animatable(0.30f)

    val carX = Animatable(-1400f)
    val carY = Animatable(-180f)
    val carRotation = Animatable(0f)

    val loadX = Animatable(1400f)
    val loadY = Animatable(180f)
    val loadRotation = Animatable(0f)

    suspend fun start() {

        // Car Entry
        carX.animateTo(
            0f,
            animationSpec = tween(
                durationMillis = 1200,
                easing = LinearEasing
            )
        )

        delay(250)

        // Load Entry
        loadX.animateTo(
            0f,
            animationSpec = tween(
                durationMillis = 1200,
                easing = LinearEasing
            )
        )

        delay(250)

        // Bike Entry
        bikeX.animateTo(
            0f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = LinearEasing
            )
        )

        bikeScale.animateTo(
            1f,
            animationSpec = tween(1000)
        )

        delay(400)

        // Car Right Turn
        carRotation.animateTo(
            35f,
            animationSpec = tween(500)
        )

        carX.animateTo(
            1300f,
            animationSpec = tween(900)
        )

        // Load Left Turn
        loadRotation.animateTo(
            -35f,
            animationSpec = tween(500)
        )

        loadX.animateTo(
            -1300f,
            animationSpec = tween(900)
        )
    }
}