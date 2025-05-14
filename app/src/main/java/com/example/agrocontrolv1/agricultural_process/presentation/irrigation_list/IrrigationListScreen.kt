package com.example.agrocontrolv1.agricultural_process.presentation.irrigation_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Colores personalizados similares a la imagen web
val LightGreen = Color(0xFF4CAF50)
val BackgroundColor = Color(0xFFF9F9F9)

data class IrrigationEntry(val date: String, val hours: Int)

@Composable
fun IrrigationListScreen() {
    val irrigationList = remember {
        mutableStateListOf(
            IrrigationEntry("2023-03-15", 2),
            IrrigationEntry("2023-04-11", 3),
            IrrigationEntry("2023-06-12", 3),
            IrrigationEntry("2023-02-10", 4),
            IrrigationEntry("2023-01-05", 2)
        )
    }

    Scaffold(
        containerColor = BackgroundColor,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Acción para agregar entrada */ },
                containerColor = LightGreen
            ) {
                Text("Add", color = Color.White)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Irrigation",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(irrigationList) { entry ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Date: ${entry.date}")
                                Text("Hours: ${entry.hours}")
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Button(
                                    onClick = { /* Acción Ver */ },
                                    colors = ButtonDefaults.buttonColors(containerColor = LightGreen)
                                ) {
                                    Text("View", color = Color.White)
                                }
                                IconButton(onClick = {
                                    irrigationList.remove(entry)
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete",
                                        tint = Color.Black
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
