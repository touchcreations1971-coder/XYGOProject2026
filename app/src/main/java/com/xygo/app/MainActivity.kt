package com.xygo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.libraries.places.api.Places
import com.xygo.app.screen.BookingConfirmScreen
import com.xygo.app.screen.DriverFoundScreen
import com.xygo.app.screen.DriverScreen
import com.xygo.app.screen.DriverSearchScreen
import com.xygo.app.screen.HomeScreen
import com.xygo.app.screen.LoadScreen
import com.xygo.app.screen.LoginScreen
import com.xygo.app.screen.RideScreen
import com.xygo.app.screen.SplashScreen
import com.xygo.app.screen.StartRideScreen
import com.xygo.app.ui.theme.XYGOTheme
import com.xygo.app.screen.DriverDashboardScreen
import androidx.compose.runtime.remember
import androidx.navigation.compose.currentBackStackEntryAsState
import com.xygo.app.screen.TripSummaryScreen
import com.xygo.app.screen.ChatDriverScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!Places.isInitialized()) {
            val applicationInfo = packageManager.getApplicationInfo(
                packageName,
                android.content.pm.PackageManager.GET_META_DATA
            )

            val apiKey = applicationInfo.metaData
                ?.getString("com.google.android.geo.API_KEY")
                ?: throw IllegalStateException("Google Maps API key not found in Manifest")

            Places.initializeWithNewPlacesApiEnabled(
                applicationContext,
                apiKey
            )
        }

        enableEdgeToEdge()


        setContent {

            XYGOTheme {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "splash"
                ) {

                    composable("splash") {

                        SplashScreen(
                            onSplashFinished = {

                                navController.navigate("home") {

                                    popUpTo("splash") {
                                        inclusive = true
                                    }
                                }
                            }
                        )
                    }

                    composable("home") {
                        HomeScreen(navController)
                    }

                    composable("ride") {
                        RideScreen(navController)
                    }

                    composable("load") {
                        LoadScreen()
                    }

                    composable("driver") {
                        DriverScreen(navController)
                    }
                    composable("driver_dashboard") {
                        DriverDashboardScreen(navController)
                    }

                    composable("login") {
                        LoginScreen()
                    }

                    composable("driver_search") {

                        val currentEntry =
                            navController.currentBackStackEntryAsState().value

                        val homeEntry =
                            remember(currentEntry) {
                                navController.getBackStackEntry("home")
                            }

                        val savedStateHandle =
                            homeEntry.savedStateHandle

                        val pickup =
                            savedStateHandle.get<String>("pickup") ?: ""

                        val drop =
                            savedStateHandle.get<String>("drop") ?: ""

                        val vehicle =
                            savedStateHandle.get<String>("vehicle") ?: "Mini"

                        val passengers =
                            savedStateHandle.get<String>("passengers") ?: "1"

                        val distanceKm =
                            savedStateHandle.get<Double>("distanceKm") ?: 0.0

                        val estimatedFare =
                            savedStateHandle.get<Int>("estimatedFare") ?: 50

                        DriverSearchScreen(
                            navController = navController,
                            pickup = pickup,
                            drop = drop,
                            vehicle = vehicle,
                            passengers = passengers,
                            distanceKm = distanceKm,
                            estimatedFare = estimatedFare
                        )
                    }

                    composable("driver_found") {

                        val currentEntry =
                            navController.currentBackStackEntryAsState().value

                        val homeEntry =
                            remember(currentEntry) {
                                navController.getBackStackEntry("home")
                            }

                        val savedStateHandle =
                            homeEntry.savedStateHandle

                        val pickup =
                            savedStateHandle.get<String>("pickup") ?: ""

                        val drop =
                            savedStateHandle.get<String>("drop") ?: ""

                        val vehicle =
                            savedStateHandle.get<String>("vehicle") ?: "Mini"

                        val passengers =
                            savedStateHandle.get<String>("passengers") ?: "1"

                        val distanceKm =
                            savedStateHandle.get<Double>("distanceKm") ?: 0.0

                        val estimatedFare =
                            savedStateHandle.get<Int>("estimatedFare") ?: 50

                        DriverFoundScreen(
                            navController = navController,
                            pickup = pickup,
                            drop = drop,
                            vehicle = vehicle,
                            passengers = passengers,
                            distanceKm = distanceKm,
                            estimatedFare = estimatedFare
                        )
                    }
                    composable("chat_driver") {

                        ChatDriverScreen(
                            navController = navController
                        )
                    }

                    composable("start_ride") {

                        val savedStateHandle =
                            navController.previousBackStackEntry
                                ?.savedStateHandle

                        val pickup =
                            savedStateHandle
                                ?.get<String>("pickup")
                                ?: ""

                        val drop =
                            savedStateHandle
                                ?.get<String>("drop")
                                ?: ""

                        val vehicle =
                            savedStateHandle
                                ?.get<String>("vehicle")
                                ?: "Mini"

                        val passengers =
                            savedStateHandle
                                ?.get<String>("passengers")
                                ?: "1"

                        val distanceKm =
                            savedStateHandle
                                ?.get<Double>("distanceKm")
                                ?: 0.0

                        val estimatedFare =
                            savedStateHandle
                                ?.get<Int>("estimatedFare")
                                ?: 0

                        StartRideScreen(
                            navController = navController,
                            pickup = pickup,
                            drop = drop,
                            vehicle = vehicle,
                            passengers = passengers,
                            distanceKm = distanceKm,
                            estimatedFare = estimatedFare
                        )
                    }
                    composable("trip_summary") {

                        val currentEntry =
                            navController.currentBackStackEntryAsState().value

                        val startRideEntry =
                            remember(currentEntry) {
                                navController.getBackStackEntry("start_ride")
                            }

                        val savedStateHandle =
                            startRideEntry.savedStateHandle

                        val pickup =
                            savedStateHandle.get<String>("pickup") ?: ""

                        val drop =
                            savedStateHandle.get<String>("drop") ?: ""

                        val vehicle =
                            savedStateHandle.get<String>("vehicle") ?: "Mini"

                        val passengers =
                            savedStateHandle.get<String>("passengers") ?: "1"

                        val distanceKm =
                            savedStateHandle.get<Double>("distanceKm") ?: 0.0

                        val estimatedFare =
                            savedStateHandle.get<Int>("estimatedFare") ?: 50

                        TripSummaryScreen(
                            navController = navController,
                            pickup = pickup,
                            drop = drop,
                            vehicle = vehicle,
                            passengers = passengers,
                            distanceKm = distanceKm,
                            estimatedFare = estimatedFare
                        )
                    }
                    composable("booking_confirm") {

                        val currentEntry =
                            navController.currentBackStackEntryAsState().value

                        val rideEntry =
                            remember(currentEntry) {
                                navController.getBackStackEntry("ride")
                            }

                        val savedStateHandle =
                            rideEntry.savedStateHandle

                        val pickup =
                            savedStateHandle.get<String>("pickup") ?: ""

                        val drop =
                            savedStateHandle.get<String>("drop") ?: ""

                        val vehicle =
                            savedStateHandle.get<String>("vehicle") ?: "Mini"

                        val passengers =
                            savedStateHandle.get<String>("passengers") ?: "1"

                        val distanceKm =
                            savedStateHandle.get<Double>("distanceKm") ?: 0.0

                        val estimatedFare =
                            savedStateHandle.get<Int>("estimatedFare") ?: 50

                        BookingConfirmScreen(
                            navController = navController,
                            pickup = pickup,
                            drop = drop,
                            vehicle = vehicle,
                            passengers = passengers,
                            distanceKm = distanceKm,
                            estimatedFare = estimatedFare
                        )
                    }

                    composable("profile") {
                        Text("My Profile")
                    }
                }
            }
        }
    }
}