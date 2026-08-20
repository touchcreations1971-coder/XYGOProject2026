package com.xygo.app.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import android.util.Log

@Composable
fun DriverSearchScreen(
    navController: NavController,
    pickup: String,
    drop: String,
    vehicle: String,
    passengers: String,
    distanceKm: Double,
    estimatedFare: Int
) {

    var angle by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {

        repeat(50) {
            angle += 20f
            delay(100)
        }
        Log.d(
            "XYGO_FLOW",
            "SEARCH DATA = pickup=$pickup, drop=$drop, distance=$distanceKm, fare=$estimatedFare"
        )

        val previousEntry =
            navController.previousBackStackEntry

        previousEntry?.savedStateHandle?.set("pickup", pickup)
        previousEntry?.savedStateHandle?.set("drop", drop)
        previousEntry?.savedStateHandle?.set("vehicle", vehicle)
        previousEntry?.savedStateHandle?.set("passengers", passengers)
        previousEntry?.savedStateHandle?.set("distanceKm", distanceKm)
        previousEntry?.savedStateHandle?.set("estimatedFare", estimatedFare)

        navController.navigate("driver_found") {
            popUpTo("driver_search") {
                inclusive = true
            }
        }
    }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF071A35)),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Surface(
                    shape = CircleShape,
                    color = Color(0xFFFFC107),
                    modifier = Modifier.size(120.dp)
                ) {

                    Box(contentAlignment = Alignment.Center) {

                        Text(
                            text = "🚖",
                            fontSize = 55.sp,
                            modifier = Modifier.rotate(angle)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(35.dp))

                Text(
                    text = "Searching Nearby Drivers...",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(15.dp))

                CircularProgressIndicator(
                    color = Color.Yellow
                )

                Spacer(modifier = Modifier.height(25.dp))

                Text(
                    text = "Please wait while we find the nearest driver.",
                    color = Color.LightGray
                )
            }
        }
    }