package com.xygo.app.screen

import androidx.compose.foundation.background
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
import androidx.compose.material3.LocalTextStyle
import com.google.firebase.database.FirebaseDatabase
import android.widget.Toast


@Composable
fun DriverScreen(
    navController: NavController
) {

    var name by remember {
        mutableStateOf("")
    }

    var mobile by remember {
        mutableStateOf("")
    }

    var vehicleType by remember {
        mutableStateOf("")
    }

    var vehicleNumber by remember {
        mutableStateOf("")
    }

    var licenceNumber by remember {
        mutableStateOf("")
    }

    var errorMessage by remember {
        mutableStateOf("")
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFF071A35)
            )
            .padding(20.dp)
    ) {

        Spacer(
            modifier = Modifier.height(15.dp)
        )


        Text(
            text = "DRIVER REGISTRATION",
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )


        Spacer(
            modifier = Modifier.height(20.dp)
        )


        // -------------------------
        // DRIVER NAME
        // -------------------------

        OutlinedTextField(
            value = name,

            onValueChange = {
                name = it
                errorMessage = ""
            },

            label = {
                Text("Driver Name")
            },
            textStyle = LocalTextStyle.current.copy(
                color = Color.White
            ),

            singleLine = true,

            modifier =
                Modifier.fillMaxWidth()
        )


        Spacer(
            modifier = Modifier.height(10.dp)
        )


        // -------------------------
        // MOBILE
        // -------------------------

        OutlinedTextField(
            value = mobile,

            onValueChange = {
                if (it.length <= 10) {
                    mobile = it.filter {
                            character ->
                        character.isDigit()
                    }
                }

                errorMessage = ""
            },

            label = {
                Text("Mobile Number")
            },
            textStyle = LocalTextStyle.current.copy(
                color = Color.White
            ),

            singleLine = true,

            modifier =
                Modifier.fillMaxWidth()
        )


        Spacer(
            modifier = Modifier.height(10.dp)
        )


        // -------------------------
        // VEHICLE TYPE
        // -------------------------

        Text(
            text = "Vehicle Type",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )


        Spacer(
            modifier = Modifier.height(6.dp)
        )


        Row(
            modifier =
                Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            DriverVehicleOption(
                name = "Bike",
                selected =
                    vehicleType == "Bike",

                onClick = {
                    vehicleType = "Bike"
                    errorMessage = ""
                },

                modifier =
                    Modifier.weight(1f)
            )


            DriverVehicleOption(
                name = "Mini",
                selected =
                    vehicleType == "Mini",

                onClick = {
                    vehicleType = "Mini"
                    errorMessage = ""
                },

                modifier =
                    Modifier.weight(1f)
            )
        }


        Spacer(
            modifier = Modifier.height(8.dp)
        )


        Row(
            modifier =
                Modifier.fillMaxWidth(),

            horizontalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            DriverVehicleOption(
                name = "Sedan",
                selected =
                    vehicleType == "Sedan",

                onClick = {
                    vehicleType = "Sedan"
                    errorMessage = ""
                },

                modifier =
                    Modifier.weight(1f)
            )


            DriverVehicleOption(
                name = "SUV",
                selected =
                    vehicleType == "SUV",

                onClick = {
                    vehicleType = "SUV"
                    errorMessage = ""
                },

                modifier =
                    Modifier.weight(1f)
            )
        }


        Spacer(
            modifier = Modifier.height(10.dp)
        )


        // -------------------------
        // VEHICLE NUMBER
        // -------------------------

        OutlinedTextField(
            value = vehicleNumber,

            onValueChange = {
                vehicleNumber = it.uppercase()
                errorMessage = ""
            },

            label = {
                Text("Vehicle Number")
            },
            textStyle = LocalTextStyle.current.copy(
                color = Color.White
            ),

            singleLine = true,

            modifier =
                Modifier.fillMaxWidth()
        )


        Spacer(
            modifier = Modifier.height(10.dp)
        )


        // -------------------------
        // LICENCE
        // -------------------------

        OutlinedTextField(
            value = licenceNumber,

            onValueChange = {
                licenceNumber = it.uppercase()
                errorMessage = ""
            },

            label = {
                Text("Driving Licence Number")
            },
            textStyle = LocalTextStyle.current.copy(
                color = Color.White
            ),

            singleLine = true,

            modifier =
                Modifier.fillMaxWidth()
        )


        Spacer(
            modifier = Modifier.height(12.dp)
        )


        // -------------------------
        // ERROR
        // -------------------------

        if (errorMessage.isNotBlank()) {

            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 14.sp
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )
        }


        // -------------------------
        // REGISTER
        // -------------------------

        Button(
            onClick = {

                when {

                    name.isBlank() -> {
                        errorMessage =
                            "Please enter driver name"
                    }

                    mobile.length != 10 -> {
                        errorMessage =
                            "Please enter valid 10 digit mobile number"
                    }

                    vehicleType.isBlank() -> {
                        errorMessage =
                            "Please select vehicle type"
                    }

                    vehicleNumber.isBlank() -> {
                        errorMessage =
                            "Please enter vehicle number"
                    }

                    licenceNumber.isBlank() -> {
                        errorMessage =
                            "Please enter driving licence number"
                    }

                    else -> {

                        errorMessage = ""

                        saveDriverToFirebase(
                            name = name,
                            mobile = mobile,
                            vehicleType = vehicleType,
                            vehicleNumber = vehicleNumber,
                            licenceNumber = licenceNumber,

                            onSuccess = { driverId ->


                                navController.navigate(
                                    "driver_dashboard"
                                )
                            },

                            onFailure = {

                                errorMessage =
                                    "Registration failed. Please try again."
                            }
                        )
                    }
                }
            },

            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(55.dp),

            shape =
                RoundedCornerShape(12.dp),

            colors =
                ButtonDefaults.buttonColors(
                    containerColor =
                        Color(0xFFFFC107)
                )
        ) {

            Text(
                text = "REGISTER DRIVER",

                color = Color.Black,

                fontSize = 18.sp,

                fontWeight =
                    FontWeight.Bold
            )
        }
    }
}


// ==================================================
// DRIVER VEHICLE OPTION
// ==================================================

@Composable
fun DriverVehicleOption(
    name: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        onClick = onClick,

        modifier =
            modifier.height(55.dp),

        shape =
            RoundedCornerShape(10.dp),

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

        Box(
            modifier =
                Modifier.fillMaxSize(),

            contentAlignment =
                Alignment.Center
        ) {

            Text(
                text = name,

                color =
                    if (selected) {
                        Color.Black
                    } else {
                        Color.White
                    },

                fontSize = 16.sp,

                fontWeight =
                    FontWeight.Bold
            )
        }
    }
}
private fun saveDriverToFirebase(
    name: String,
    mobile: String,
    vehicleType: String,
    vehicleNumber: String,
    licenceNumber: String,
    onSuccess: (String) -> Unit,
    onFailure: () -> Unit
) {

    val database = FirebaseDatabase.getInstance()

    val driverId =
        database
            .getReference("drivers")
            .push()
            .key

    if (driverId == null) {
        onFailure()
        return
    }

    val driverData =
        mapOf(
            "name" to name,
            "mobile" to mobile,
            "vehicleType" to vehicleType,
            "vehicleNumber" to vehicleNumber,
            "licenceNumber" to licenceNumber,
            "online" to false
        )

    database
        .getReference("drivers")
        .child(driverId)
        .setValue(driverData)
        .addOnSuccessListener {
            onSuccess(driverId)
        }
        .addOnFailureListener {
            onFailure()
        }
}