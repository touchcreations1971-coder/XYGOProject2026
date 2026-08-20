package com.xygo.app.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
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
fun BookingConfirmScreen(
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
                vertical = 10.dp
            )
            .navigationBarsPadding(),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ==============================
        // HEADER
        // ==============================

        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = Color.Green,
            modifier = Modifier.size(55.dp)
        )

        Text(
            text = "CONFIRM YOUR RIDE",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        // ==============================
        // RIDE DETAILS
        // ==============================

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF12355B)
            )
        ) {

            Column(
                modifier = Modifier.padding(14.dp)
            ) {

                Text(
                    text = "Pickup",
                    color = Color.LightGray,
                    fontSize = 12.sp
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

                Text(
                    text = "Destination",
                    color = Color.LightGray,
                    fontSize = 12.sp
                )

                Text(
                    text = drop,
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(7.dp)
                )

                Text(
                    text = "Vehicle: $vehicle",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(7.dp)
                )

                Text(
                    text = "Passengers: $passengers",
                    color = Color.White,
                    fontSize = 15.sp
                )

                Spacer(
                    modifier = Modifier.height(7.dp)
                )

                Text(
                    text = "Distance: ${"%.1f".format(distanceKm)} km",
                    color = Color.White,
                    fontSize = 15.sp
                )

                Spacer(
                    modifier = Modifier.height(7.dp)
                )

                Text(
                    text = "Estimated Fare: ₹$estimatedFare",
                    color = Color(0xFFFFC107),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Please check your ride details before confirming.",
            color = Color.LightGray,
            fontSize = 12.sp
        )

        // खाली जगह
        Spacer(
            modifier = Modifier.weight(1f)
        )

        // ==============================
        // CONFIRM BOOKING
        // ==============================

        Button(
            onClick = {

                val database =
                    FirebaseDatabase.getInstance()

                val rideId =
                    database
                        .reference
                        .child("rides")
                        .push()
                        .key

                if (rideId != null) {

                    val rideData = mapOf(
                        "rideId" to rideId,
                        "pickup" to pickup,
                        "drop" to drop,
                        "vehicle" to vehicle,
                        "passengers" to passengers,
                        "distanceKm" to distanceKm,
                        "estimatedFare" to estimatedFare,
                        "status" to "SEARCHING"
                    )

                    database
                        .reference
                        .child("rides")
                        .child(rideId)
                        .setValue(rideData)
                        .addOnSuccessListener {

                            navController.navigate("driver_search") {
                                launchSingleTop = true
                            }
                        }
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFC107)
            )
        ) {

            Text(
                text = "CONFIRM BOOKING",
                color = Color.Black,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.height(7.dp)
        )

        // ==============================
        // EDIT RIDE
        // ==============================

        OutlinedButton(
            onClick = {
                navController.popBackStack()
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
        ) {

            Text(
                text = "EDIT RIDE",
                color = Color.White,
                fontSize = 15.sp
            )
        }

        Spacer(
            modifier = Modifier.height(5.dp)
        )
    }
}