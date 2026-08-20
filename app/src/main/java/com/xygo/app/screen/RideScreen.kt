package com.xygo.app.screen

import android.util.Log

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController

import com.google.android.libraries.places.widget.PlaceAutocomplete
import com.google.android.libraries.places.widget.PlaceAutocompleteActivity

import com.xygo.app.getRouteDistance


@Composable
fun RideScreen(
    navController: NavController
) {

    var pickup by remember {
        mutableStateOf("")
    }

    var drop by remember {
        mutableStateOf("")
    }

    var passengers by remember {
        mutableStateOf("1")
    }

    var vehicle by remember {
        mutableStateOf("Mini")
    }

    var distanceKm by remember {
        mutableStateOf(0.0)
    }

    var isCalculatingFare by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current


    // =====================================
    // PICKUP
    // =====================================

    val pickupLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.StartActivityForResult()
        ) { result ->

            Log.d(
                "XYGO_ROUTE",
                "PICKUP CALLBACK CALLED"
            )

            val data = result.data

            Log.d(
                "XYGO_ROUTE",
                "PICKUP RESULT CODE = ${result.resultCode}, DATA = $data"
            )

            if (
                result.resultCode ==
                PlaceAutocompleteActivity.RESULT_OK &&
                data != null
            ) {

                val prediction =
                    PlaceAutocomplete
                        .getPredictionFromIntent(data)

                if (prediction != null) {

                    pickup =
                        prediction
                            .getFullText(null)
                            .toString()

                    Log.d(
                        "XYGO_ROUTE",
                        "PICKUP SELECTED = $pickup"
                    )
                }
            }
        }


    // =====================================
    // DROP
    // =====================================

    val dropLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts.StartActivityForResult()
        ) { result ->

            Log.d(
                "XYGO_ROUTE",
                "DROP CALLBACK CALLED"
            )

            val data = result.data

            Log.d(
                "XYGO_ROUTE",
                "DROP RESULT CODE = ${result.resultCode}, DATA = $data"
            )

            if (
                result.resultCode ==
                PlaceAutocompleteActivity.RESULT_OK &&
                data != null
            ) {

                val prediction =
                    PlaceAutocomplete
                        .getPredictionFromIntent(data)

                if (prediction != null) {

                    drop =
                        prediction
                            .getFullText(null)
                            .toString()

                    Log.d(
                        "XYGO_ROUTE",
                        "DROP SELECTED = $drop"
                    )
                }
            }
        }


    // =====================================
    // DISTANCE
    // =====================================

    LaunchedEffect(
        pickup,
        drop
    ) {

        if (
            pickup.isNotBlank() &&
            drop.isNotBlank()
        ) {

            isCalculatingFare = true

            val result =
                getRouteDistance(
                    context = context,
                    pickup = pickup,
                    drop = drop
                )

            Log.d(
                "XYGO_ROUTE",
                "Distance result = $result"
            )

            if (result != null) {
                distanceKm = result
            }

            isCalculatingFare = false
        }
    }


    // =====================================
    // FARE
    // =====================================

    val baseFare =
        when (vehicle) {

            "Bike" -> 30.0
            "Mini" -> 50.0
            "Sedan" -> 70.0
            "SUV" -> 100.0

            else -> 50.0
        }


    val perKmRate =
        when (vehicle) {

            "Bike" -> 8.0
            "Mini" -> 12.0
            "Sedan" -> 15.0
            "SUV" -> 20.0

            else -> 12.0
        }


    val estimatedFare =
        baseFare +
                (distanceKm * perKmRate)


    // =====================================
    // SCREEN
    // =====================================

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    Color(0xFF071A35)
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 10.dp
                ),

        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Spacer(
            modifier =
                Modifier.height(10.dp)
        )


        Text(
            text = "BOOK A RIDE",
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )


        Spacer(
            modifier =
                Modifier.height(15.dp)
        )


        // =====================================
        // PICKUP
        // =====================================

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(65.dp)
        ) {

            OutlinedTextField(

                value = pickup,

                onValueChange = {},

                readOnly = true,

                label = {
                    Text(
                        text = "Pickup Location"
                    )
                },

                textStyle =
                    LocalTextStyle.current.copy(
                        color = Color.White,
                        fontSize = 15.sp
                    ),

                singleLine = true,

                modifier =
                    Modifier
                        .fillMaxWidth()
            )


            Box(
                modifier =
                    Modifier
                        .matchParentSize()
                        .clickable {

                            Log.d(
                                "XYGO_ROUTE",
                                "PICKUP CLICKED"
                            )

                            val intent =
                                PlaceAutocomplete
                                    .createIntent(
                                        context
                                    ) {

                                        setInitialQuery("")
                                    }

                            pickupLauncher.launch(
                                intent
                            )
                        }
            )
        }


        Spacer(
            modifier =
                Modifier.height(10.dp)
        )


        // =====================================
        // DROP
        // =====================================

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(65.dp)
        ) {

            OutlinedTextField(

                value = drop,

                onValueChange = {},

                readOnly = true,

                label = {
                    Text(
                        text =
                            "Where do you want to go?"
                    )
                },

                textStyle =
                    LocalTextStyle.current.copy(
                        color = Color.White,
                        fontSize = 15.sp
                    ),

                singleLine = true,

                modifier =
                    Modifier
                        .fillMaxWidth()
            )


            Box(
                modifier =
                    Modifier
                        .matchParentSize()
                        .clickable {

                            Log.d(
                                "XYGO_ROUTE",
                                "DROP CLICKED"
                            )

                            val intent =
                                PlaceAutocomplete
                                    .createIntent(
                                        context
                                    ) {

                                        setInitialQuery("")
                                    }

                            dropLauncher.launch(
                                intent
                            )
                        }
            )
        }


        Spacer(
            modifier =
                Modifier.height(12.dp)
        )


        // =====================================
        // PASSENGERS
        // =====================================

        Text(
            text = "Passengers",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )


        Spacer(
            modifier =
                Modifier.height(5.dp)
        )


        OutlinedTextField(

            value = passengers,

            onValueChange = {
                passengers = it
            },

            singleLine = true,

            modifier =
                Modifier
                    .fillMaxWidth(),

            textStyle = LocalTextStyle.current.copy(
                color = Color.White
            ),

            label = {
                Text(
                    text = "Number of Passengers",
                    color = Color.White
                )
            },

            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White
            )
        )


        Spacer(
            modifier =
                Modifier.height(12.dp)
        )


        // =====================================
        // VEHICLE
        // =====================================

        Text(
            text = "Choose Vehicle",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )


        Spacer(
            modifier =
                Modifier.height(8.dp)
        )


        Row(
            modifier =
                Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            VehicleOption(
                emoji = "🏍️",
                name = "Bike",
                selected =
                    vehicle == "Bike",

                onClick = {
                    vehicle = "Bike"
                },

                modifier =
                    Modifier.weight(1f)
            )


            VehicleOption(
                emoji = "🚗",
                name = "Mini",
                selected =
                    vehicle == "Mini",

                onClick = {
                    vehicle = "Mini"
                },

                modifier =
                    Modifier.weight(1f)
            )
        }


        Spacer(
            modifier =
                Modifier.height(8.dp)
        )


        Row(
            modifier =
                Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            VehicleOption(
                emoji = "🚘",
                name = "Sedan",
                selected =
                    vehicle == "Sedan",

                onClick = {
                    vehicle = "Sedan"
                },

                modifier =
                    Modifier.weight(1f)
            )


            VehicleOption(
                emoji = "🚙",
                name = "SUV",
                selected =
                    vehicle == "SUV",

                onClick = {
                    vehicle = "SUV"
                },

                modifier =
                    Modifier.weight(1f)
            )
        }


        Spacer(
            modifier =
                Modifier.height(10.dp)
        )


        // =====================================
        // DISTANCE
        // =====================================

        Text(
            text =
                if (isCalculatingFare) {

                    "Calculating distance..."

                } else {

                    "📏 Distance: ${
                        String.format(
                            "%.1f",
                            distanceKm
                        )
                    } km"
                },

            color = Color.White,
            fontSize = 16.sp
        )


        Spacer(
            modifier =
                Modifier.height(5.dp)
        )


        // =====================================
        // FARE
        // =====================================

        Text(
            text =
                "💰 Estimated Fare: ₹${
                    estimatedFare.toInt()
                }",

            color =
                Color(0xFFFFC107),

            fontSize = 20.sp,

            fontWeight =
                FontWeight.Bold
        )


        Spacer(
            modifier =
                Modifier.height(12.dp)
        )


        // =====================================
        // BOOK NOW
        // =====================================

        Button(

            onClick = {

                val rideEntry =
                    navController
                        .getBackStackEntry(
                            "ride"
                        )

                rideEntry
                    .savedStateHandle
                    .set(
                        "pickup",
                        pickup
                    )

                rideEntry
                    .savedStateHandle
                    .set(
                        "drop",
                        drop
                    )

                rideEntry
                    .savedStateHandle
                    .set(
                        "vehicle",
                        vehicle
                    )

                rideEntry
                    .savedStateHandle
                    .set(
                        "passengers",
                        passengers
                    )

                rideEntry
                    .savedStateHandle
                    .set(
                        "distanceKm",
                        distanceKm
                    )

                rideEntry
                    .savedStateHandle
                    .set(
                        "estimatedFare",
                        estimatedFare.toInt()
                    )

                Log.d(
                    "XYGO_FLOW",
                    "BOOKING SAVED = pickup=$pickup, drop=$drop, distance=$distanceKm, fare=${estimatedFare.toInt()}"
                )

                navController.navigate(
                    "booking_confirm"
                ) {
                    launchSingleTop = true
                }
            },

            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(55.dp),

            shape =
                RoundedCornerShape(28.dp),

            colors =
                ButtonDefaults.buttonColors(
                    containerColor =
                        Color(0xFFFFC107)
                )
        ) {

            Text(
                text = "🚗 BOOK NOW",
                color = Color.Black,
                fontSize = 17.sp,
                fontWeight =
                    FontWeight.Bold
            )
        }
    }
}


// =====================================
// VEHICLE OPTION
// =====================================

@Composable
fun VehicleOption(

    emoji: String,

    name: String,

    selected: Boolean,

    onClick: () -> Unit,

    modifier: Modifier =
        Modifier

) {

    Card(

        onClick = onClick,

        modifier =
            modifier.height(55.dp),

        shape =
            RoundedCornerShape(12.dp),

        colors =
            CardDefaults.cardColors(

                containerColor =
                    if (selected) {

                        Color(0xFFFFC107)

                    } else {

                        Color(0xFF12355B)
                    }
            )
    ) {

        Row(

            modifier =
                Modifier.fillMaxSize(),

            horizontalArrangement =
                Arrangement.Center,

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Text(
                text = emoji,
                fontSize = 21.sp
            )


            Spacer(
                modifier =
                    Modifier.width(5.dp)
            )


            Text(

                text = name,

                color =
                    if (selected) {

                        Color.Black

                    } else {

                        Color.White
                    },

                fontSize = 14.sp,

                fontWeight =
                    FontWeight.Bold
            )
        }
    }
}