package com.xygo.app.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.database.FirebaseDatabase

@Composable
fun DriverDashboardScreen(
    navController: NavController
) {

    var isOnline by remember {
        mutableStateOf(false)
    }

    var rideRequestVisible by remember {
        mutableStateOf(false)
    }

    var rideAccepted by remember {
        mutableStateOf(false)
    }

    var pickup by remember {
        mutableStateOf("")
    }

    var drop by remember {
        mutableStateOf("")
    }

    var vehicle by remember {
        mutableStateOf("")
    }

    var passengers by remember {
        mutableStateOf("")
    }

    var distanceKm by remember {
        mutableStateOf(0.0)
    }

    var estimatedFare by remember {
        mutableStateOf(0)
    }

    var currentRideId by remember {
        mutableStateOf<String?>(null)
    }


    // =====================================
// GET RIDE FROM FIREBASE
// =====================================

    LaunchedEffect(isOnline) {

        if (!isOnline) {

            rideRequestVisible = false
            rideAccepted = false
            currentRideId = null

        } else {

            val rideQuery =
                FirebaseDatabase
                    .getInstance()
                    .reference
                    .child("rides")
                    .orderByChild("status")
                    .equalTo("SEARCHING")
                    .limitToFirst(1)

            rideQuery
                .addValueEventListener(
                    object :
                        com.google.firebase.database.ValueEventListener {

                        override fun onDataChange(
                            snapshot:
                            com.google.firebase.database.DataSnapshot
                        ) {

                            if (
                                snapshot.exists() &&
                                !rideAccepted
                            ) {

                                for (child in snapshot.children) {

                                    currentRideId = child.key

                                    pickup =
                                        child.child("pickup")
                                            .getValue(String::class.java)
                                            ?: ""

                                    drop =
                                        child.child("drop")
                                            .getValue(String::class.java)
                                            ?: ""

                                    vehicle =
                                        child.child("vehicle")
                                            .getValue(String::class.java)
                                            ?: ""

                                    passengers =
                                        child.child("passengers")
                                            .getValue(String::class.java)
                                            ?: ""

                                    distanceKm =
                                        (child.child("distanceKm").value
                                                as? Number)
                                            ?.toDouble()
                                            ?: 0.0

                                    estimatedFare =
                                        (child.child("estimatedFare").value
                                                as? Number)
                                            ?.toInt()
                                            ?: 0

                                    rideRequestVisible = true

                                    break
                                }

                            } else if (!rideAccepted) {

                                rideRequestVisible = false
                                currentRideId = null
                            }
                        }

                        override fun onCancelled(
                            error:
                            com.google.firebase.database.DatabaseError
                        ) {

                            rideRequestVisible = false
                        }
                    }
                )
        }
    }

    // =====================================
    // SCREEN
    // =====================================

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF071A35))
            .padding(20.dp)
            .verticalScroll(
                rememberScrollState()
            )
    ) {

        Spacer(
            modifier = Modifier.height(20.dp)
        )


        // =====================================
        // TITLE
        // =====================================

        Text(
            text = "DRIVER DASHBOARD",
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(25.dp)
        )


        // =====================================
        // DRIVER CARD
        // =====================================

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF12355B)
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Welcome Driver",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "Vehicle: Mini",
                    color = Color.LightGray,
                    fontSize = 16.sp
                )

                Text(
                    text = "Vehicle No: HP-00-0000",
                    color = Color.LightGray,
                    fontSize = 16.sp
                )
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )


        // =====================================
        // ONLINE / OFFLINE
        // =====================================

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF12355B)
            )
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 15.dp
                    ),

                verticalAlignment =
                    Alignment.CenterVertically,

                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                Column {

                    Text(
                        text = "Driver Status",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text =
                            if (isOnline)
                                "You are Online"
                            else
                                "You are Offline",

                        color =
                            if (isOnline)
                                Color.Green
                            else
                                Color.LightGray
                    )
                }

                Switch(
                    checked = isOnline,

                    onCheckedChange = {

                        isOnline = it

                        if (it) {
                            rideAccepted = false
                        }
                    }
                )
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )


        // =====================================
        // NEW RIDE REQUEST
        // =====================================

        if (
            rideRequestVisible &&
            isOnline &&
            !rideAccepted
        ) {

            Text(
                text = "NEW RIDE REQUEST",
                color = Color(0xFFFFC107),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF12355B)
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Pickup",
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )

                    Text(
                        text = pickup,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Text(
                        text = "Drop",
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )

                    Text(
                        text = drop,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Text(
                        text =
                            "Distance: ${
                                String.format(
                                    "%.1f",
                                    distanceKm
                                )
                            } km",

                        color = Color.White,
                        fontSize = 16.sp
                    )

                    Spacer(
                        modifier = Modifier.height(5.dp)
                    )

                    Text(
                        text = "Passengers: $passengers",
                        color = Color.White,
                        fontSize = 16.sp
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Text(
                        text = "Fare: ₹$estimatedFare",
                        color = Color(0xFFFFC107),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(15.dp)
                    )


                    // =====================================
                    // ACCEPT / REJECT
                    // =====================================

                    Row(
                        modifier = Modifier.fillMaxWidth(),

                        horizontalArrangement =
                            Arrangement.spacedBy(10.dp)
                    ) {

                        OutlinedButton(
                            onClick = {

                                rideRequestVisible = false
                            },

                            modifier =
                                Modifier
                                    .weight(1f)
                                    .height(50.dp)
                        ) {

                            Text(
                                text = "REJECT",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }


                        Button(
                            onClick = {

                                val rideId = currentRideId

                                if (rideId != null) {

                                    FirebaseDatabase
                                        .getInstance()
                                        .reference
                                        .child("rides")
                                        .child(rideId)
                                        .child("status")
                                        .setValue("ACCEPTED")
                                        .addOnSuccessListener {

                                            rideAccepted = true
                                            rideRequestVisible = false
                                        }
                                        .addOnFailureListener {

                                            rideAccepted = true
                                            rideRequestVisible = false
                                        }

                                } else {

                                    rideAccepted = true
                                    rideRequestVisible = false
                                }
                            },

                            modifier =
                                Modifier
                                    .weight(1f)
                                    .height(50.dp),

                            colors =
                                ButtonDefaults.buttonColors(
                                    containerColor =
                                        Color(0xFFFFC107)
                                )
                        ) {

                            Text(
                                text = "ACCEPT RIDE",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }


        // =====================================
        // RIDE ACCEPTED
        // =====================================

        if (rideAccepted) {

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF12355B)
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "✓ RIDE ACCEPTED",
                        color = Color.Green,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Text(
                        text = "Pickup: $pickup",
                        color = Color.White
                    )

                    Text(
                        text = "Drop: $drop",
                        color = Color.White
                    )

                    Text(
                        text = "Distance: ${String.format("%.1f", distanceKm)} km",
                        color = Color.White
                    )

                    Text(
                        text = "Passengers: $passengers",
                        color = Color.White
                    )

                    Text(
                        text = "Fare: ₹$estimatedFare",
                        color = Color(0xFFFFC107),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(15.dp)
                    )


                    // =====================================
                    // START RIDE
                    // =====================================

                    Button(
                        onClick = {

                            val savedStateHandle =
                                navController.currentBackStackEntry
                                    ?.savedStateHandle

                            if (savedStateHandle != null) {

                                savedStateHandle.set(
                                    "rideId",
                                    currentRideId
                                )

                                savedStateHandle.set(
                                    "pickup",
                                    pickup
                                )

                                savedStateHandle.set(
                                    "drop",
                                    drop
                                )

                                savedStateHandle.set(
                                    "vehicle",
                                    vehicle
                                )

                                savedStateHandle.set(
                                    "passengers",
                                    passengers
                                )

                                savedStateHandle.set(
                                    "distanceKm",
                                    distanceKm
                                )

                                savedStateHandle.set(
                                    "estimatedFare",
                                    estimatedFare
                                )

                                val rideId = currentRideId

                                if (rideId != null) {

                                    FirebaseDatabase
                                        .getInstance()
                                        .reference
                                        .child("rides")
                                        .child(rideId)
                                        .child("status")
                                        .setValue("STARTED")
                                }

                                navController.navigate("start_ride") {
                                    launchSingleTop = true
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
                            text = "START RIDE",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )
                    }
                }
            }
        }


        // =====================================
        // TODAY'S RIDES
        // =====================================

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "Today's Rides",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF12355B)
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text =
                        if (rideAccepted)
                            "1 ride accepted"
                        else
                            "No rides yet",

                    color = Color.LightGray,
                    fontSize = 17.sp
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "New ride requests will appear here.",
                    color = Color.White
                )
            }
        }


        // =====================================
        // EARNINGS
        // =====================================

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF12355B)
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Today's Earnings",
                    color = Color.LightGray,
                    fontSize = 16.sp
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    text =
                        if (rideAccepted)
                            "₹$estimatedFare"
                        else
                            "₹0",

                    color = Color(0xFFFFC107),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }


        // =====================================
        // BACK TO HOME
        // =====================================

        Spacer(
            modifier = Modifier.height(25.dp)
        )

        OutlinedButton(
            onClick = {

                navController.navigate("home")
            },

            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(52.dp),

            shape =
                RoundedCornerShape(12.dp)
        ) {

            Text(
                text = "BACK TO HOME",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )
    }
}