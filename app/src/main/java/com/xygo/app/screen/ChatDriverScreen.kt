package com.xygo.app.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

@Composable
fun ChatDriverScreen(
    navController: NavController
) {

    var message by remember {
        mutableStateOf("")
    }

    var messages by remember {
        mutableStateOf(
            listOf(
                "Rahul Kumar: Hello! I am your driver."
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF071A35))
            .navigationBarsPadding()
    ) {

        // ==============================
        // HEADER
        // ==============================

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF12355B))
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "Rahul Kumar",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "🟢 Online",
                    color = Color.Green,
                    fontSize = 13.sp
                )
            }

            TextButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {

                Text(
                    text = "BACK",
                    color = Color.White
                )
            }
        }

        // ==============================
        // MESSAGES
        // ==============================

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(12.dp),

            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(messages) { msg ->

                val isDriver =
                    msg.startsWith("Rahul Kumar:")

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement =
                        if (isDriver) {
                            Arrangement.Start
                        } else {
                            Arrangement.End
                        }
                ) {

                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor =
                                if (isDriver) {
                                    Color(0xFF12355B)
                                } else {
                                    Color(0xFFFFC107)
                                }
                        )
                    ) {

                        Text(
                            text = msg,
                            color =
                                if (isDriver) {
                                    Color.White
                                } else {
                                    Color.Black
                                },

                            modifier = Modifier.padding(
                                horizontal = 14.dp,
                                vertical = 10.dp
                            ),

                            fontSize = 15.sp
                        )
                    }
                }
            }
        }

        // ==============================
        // MESSAGE BOX
        // ==============================

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF12355B))
                .padding(10.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            OutlinedTextField(
                value = message,

                onValueChange = {
                    message = it
                },

                modifier = Modifier.weight(1f),

                singleLine = true,

                textStyle = LocalTextStyle.current.copy(
                    color = Color.White
                ),

                placeholder = {
                    Text(
                        text = "Type a message...",
                        color = Color.LightGray
                    )
                },

                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedPlaceholderColor = Color.LightGray,
                    unfocusedPlaceholderColor = Color.LightGray
                )
            )
            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Button(
                onClick = {

                    if (message.isNotBlank()) {

                        messages =
                            messages +
                                    "You: $message"

                        message = ""
                    }
                },

                modifier = Modifier.height(55.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor =
                        Color(0xFFFFC107)
                )
            ) {

                Text(
                    text = "SEND",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}