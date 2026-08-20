package com.xygo.app.screen

import android.content.Intent
import android.net.Uri

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext

@Composable
fun DriverFoundScreen(
    navController: NavController,
    pickup: String,
    drop: String,
    vehicle: String,
    passengers: String,
    distanceKm: Double,
    estimatedFare: Int
) {

    val context = LocalContext.current

    // Driver ka temporary demo number
    val driverPhone = "9876543210"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF071A35))
            .padding(
                horizontal = 16.dp,
                vertical = 10.dp
            )
            .navigationBarsPadding(),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "✅ Driver Found",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF12355B)
            )
        ) {

            Column(
                modifier = Modifier.padding(14.dp)
            ) {

                Text(
                    text = "👤 Rahul Kumar",
                    color = Color.White,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "⭐ 4.8 Rating",
                    color = Color.Yellow,
                    fontSize = 15.sp
                )

                Spacer(
                    modifier = Modifier.height(7.dp)
                )

                Text(
                    text = "🚗 Swift Dzire",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "HP 18A 1234",
                    color = Color.LightGray,
                    fontSize = 14.sp
                )

                Spacer(
                    modifier = Modifier.height(9.dp)
                )

                Text(
                    text = "📍 Pickup",
                    color = Color.LightGray,
                    fontSize = 12.sp
                )

                Text(
                    text = pickup,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    text = "📍 Drop",
                    color = Color.LightGray,
                    fontSize = 12.sp
                )

                Text(
                    text = drop,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(7.dp)
                )

                Text(
                    text = "📏 Distance: ${
                        "%.1f".format(distanceKm)
                    } km",
                    color = Color.White,
                    fontSize = 15.sp
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "👥 Passengers: $passengers",
                    color = Color.White,
                    fontSize = 14.sp
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "💰 Fare: ₹$estimatedFare",
                    color = Color(0xFFFFC107),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    text = "🟢 Arriving in 5 minutes",
                    color = Color.Green,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        // ==============================
        // CALL DRIVER
        // ==============================

        Button(
            onClick = {

                val intent = Intent(
                    Intent.ACTION_DIAL,
                    Uri.parse("tel:$driverPhone")
                )

                context.startActivity(intent)
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
        ) {

            Text(
                text = "📞 CALL DRIVER",
                fontSize = 16.sp
            )
        }

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        // ==============================
        // CHAT DRIVER
        // ==============================

        Button(
            onClick = {
                navController.navigate("chat_driver")
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
        ) {

            Text(
                text = "💬 CHAT DRIVER",
                fontSize = 16.sp
            )
        }

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        // ==============================
        // START RIDE
        // ==============================

        Button(
            onClick = {


                navController
                    .currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("pickup", pickup)

                navController
                    .currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("drop", drop)

                navController
                    .currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("vehicle", vehicle)

                navController
                    .currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("passengers", passengers)

                navController
                    .currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("distanceKm", distanceKm)

                navController
                    .currentBackStackEntry
                    ?.savedStateHandle
                    ?.set("estimatedFare", estimatedFare)

                navController.navigate(
                    "start_ride"
                )
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFC107)
            )
        ) {

            Text(
                text = "START RIDE",
                color = Color.Black,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.height(5.dp)
        )
    }
}