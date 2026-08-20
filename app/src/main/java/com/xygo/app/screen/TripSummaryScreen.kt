package com.xygo.app.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TripSummaryScreen(
    navController: NavController,
    pickup: String,
    drop: String,
    vehicle: String,
    passengers: String,
    distanceKm: Double,
    estimatedFare: Int
) {

    var selectedRating by remember {
        mutableStateOf(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF071A35))
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            )
            .navigationBarsPadding(),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ==============================
        // HEADER
        // ==============================

        Text(
            text = "✅ RIDE COMPLETED",
            color = Color.White,
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        // ==============================
        // TRIP SUMMARY CARD
        // ==============================

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF12355B)
            )
        ) {

            Column(
                modifier = Modifier.padding(15.dp)
            ) {

                Text(
                    text = "Trip Summary",
                    color = Color.White,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text = "👤 Driver",
                    color = Color.LightGray,
                    fontSize = 13.sp
                )

                Text(
                    text = "Rahul Kumar",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(7.dp)
                )

                Text(
                    text = "🚗 Vehicle",
                    color = Color.LightGray,
                    fontSize = 13.sp
                )

                Text(
                    text = "Swift Dzire • HP 18A 1234",
                    color = Color.White,
                    fontSize = 15.sp
                )

                Spacer(
                    modifier = Modifier.height(9.dp)
                )

                // PICKUP

                Text(
                    text = "📍 Pickup",
                    color = Color.LightGray,
                    fontSize = 13.sp
                )

                Text(
                    text = pickup,
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(7.dp)
                )

                // DROP

                Text(
                    text = "📍 Drop",
                    color = Color.LightGray,
                    fontSize = 13.sp
                )

                Text(
                    text = drop,
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(9.dp)
                )

                Text(
                    text = "📏 Distance: ${
                        "%.1f".format(distanceKm)
                    } km",

                    color = Color.White,
                    fontSize = 15.sp
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    text = "👥 Passengers: $passengers",
                    color = Color.White,
                    fontSize = 15.sp
                )

                Spacer(
                    modifier = Modifier.height(7.dp)
                )

                Text(
                    text = "💰 Final Fare: ₹$estimatedFare",
                    color = Color(0xFFFFC107),
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        // ==============================
        // RATING
        // ==============================

        Text(
            text = "How was your ride?",
            color = Color.White,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            for (star in 1..5) {

                Text(
                    text = "★",

                    color =
                        if (star <= selectedRating) {
                            Color(0xFFFFC107)
                        } else {
                            Color.Gray
                        },

                    fontSize = 38.sp,

                    modifier = Modifier
                        .clickable {
                            selectedRating = star
                        }
                        .padding(
                            horizontal = 3.dp
                        )
                )
            }
        }

        Spacer(
            modifier = Modifier.height(5.dp)
        )

        if (selectedRating > 0) {

            Text(
                text = "$selectedRating / 5",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.weight(1f)
        )

        // ==============================
        // SUBMIT RATING
        // ==============================

        Button(
            onClick = {

                if (selectedRating > 0) {

                    navController.navigate("home") {

                        popUpTo("home") {
                            inclusive = false
                        }

                        launchSingleTop = true
                    }
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor =
                    if (selectedRating > 0) {
                        Color(0xFFFFC107)
                    } else {
                        Color(0xFF52616B)
                    }
            )
        ) {

            Text(
                text = "SUBMIT RATING",
                color =
                    if (selectedRating > 0) {
                        Color.Black
                    } else {
                        Color.LightGray
                    },

                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        // ==============================
        // SKIP
        // ==============================

        OutlinedButton(
            onClick = {

                navController.navigate("home") {

                    popUpTo("home") {
                        inclusive = false
                    }

                    launchSingleTop = true
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
        ) {

            Text(
                text = "SKIP",
                color = Color.White,
                fontSize = 15.sp
            )
        }

        Spacer(
            modifier = Modifier.height(4.dp)
        )
    }
}