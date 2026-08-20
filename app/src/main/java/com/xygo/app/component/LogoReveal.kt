package com.xygo.app.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.foundation.Image
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import com.xygo.app.R

@Composable
fun LogoReveal(
    visible: Boolean,
    modifier: Modifier = Modifier
) {

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        label = "logoAlpha"
    )

    Image(
        painter = painterResource(R.drawable.xygo_logo),
        contentDescription = "XYGO Logo",
        modifier = modifier.alpha(alpha)
    )
}