package com.xygo.app.screen

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
import com.google.firebase.database.FirebaseDatabase

@Composable
fun StartRideScreen(
    navController: NavController,
    pickup: String,
    drop: String,
    vehicle: String,
    passengers: String,
    distanceKm: Double,
    estimatedFare: Int
) {

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

        Text(
            text = "🚗 Ride Started",
            color = Color.White,
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF12355B)
            )
        ) {

            Column(
                modifier = Modifier.padding(18.dp)
            ) {

                Text(
                    text = "Rahul Kumar",
                    color = Color.White,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(7.dp)
                )

                Text(
                    text = "🚗 Swift Dzire",
                    color = Color.White,
                    fontSize = 18.sp
                )

                Text(
                    text = "HP 18A 1234",
                    color = Color.LightGray,
                    fontSize = 16.sp
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = "📍 Ride in Progress",
                    color = Color.Green,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "📍 Pickup",
            color = Color.LightGray,
            fontSize = 13.sp
        )

        Text(
            text = pickup,
            color = Color.White,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "📍 Drop",
            color = Color.LightGray,
            fontSize = 13.sp
        )

        Text(
            text = drop,
            color = Color.White,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "📏 Distance: ${
                "%.1f".format(distanceKm)
            } km",
            color = Color.White,
            fontSize = 15.sp
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = "💰 Estimated Fare: ₹$estimatedFare",
            color = Color(0xFFFFC107),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.weight(1f)
        )

        Button(
            onClick = {

                val rideId =
                    navController
                        .getBackStackEntry("start_ride")
                        .savedStateHandle
                        ?.get<String>("rideId")

                if (rideId != null) {

                    FirebaseDatabase
                        .getInstance()
                        .reference
                        .child("rides")
                        .child(rideId)
                        .child("status")
                        .setValue("COMPLETED")
                }

                navController
                    .getBackStackEntry("start_ride")
                    .savedStateHandle
                    ?.set("pickup", pickup)

                navController
                    .getBackStackEntry("start_ride")
                    .savedStateHandle
                    ?.set("drop", drop)

                navController
                    .getBackStackEntry("start_ride")
                    .savedStateHandle
                    ?.set("vehicle", vehicle)

                navController
                    .getBackStackEntry("start_ride")
                    .savedStateHandle
                    ?.set("passengers", passengers)

                navController
                    .getBackStackEntry("start_ride")
                    .savedStateHandle
                    ?.set("distanceKm", distanceKm)

                navController
                    .getBackStackEntry("start_ride")
                    .savedStateHandle
                    ?.set("estimatedFare", estimatedFare)

                navController.navigate("trip_summary")
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFC107)
            )
        ) {

            Text(
                text = "RIDE COMPLETED",
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