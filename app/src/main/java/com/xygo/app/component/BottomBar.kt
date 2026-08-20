package com.xygo.app.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun BottomBar(
    navController: NavController
) {

    NavigationBar(
        containerColor = Color(0xFF071A35)
    ) {

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("home") },
            icon = { Icon(Icons.Default.Home, null) },
            label = { Text("Home") }
        )

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("bookings") },
            icon = { Icon(Icons.Default.Book, null) },
            label = { Text("Bookings") }
        )

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("support") },
            icon = { Icon(Icons.Default.SupportAgent, null) },
            label = { Text("Support") }
        )

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("profile") },
            icon = { Icon(Icons.Default.Person, null) },
            label = { Text("Profile") }
        )
    }
}