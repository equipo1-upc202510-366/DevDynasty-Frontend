package com.example.agrocontrolv1.agricultural_process.presentation.irrigation_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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

val LightGreen = Color(0xFF4CAF50)
val BackgroundColor = Color(0xFFF9F9F9)

// Modelo simple de entrada de riego
data class IrrigationEntry(val date: String, val hours: Int)

@Composable
fun IrrigationListScreen(
    navigateBack: () -> Unit,
    navigateToEdit: (IrrigationEntry) -> Unit,
    navigateToNew: () -> Unit
) {
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
                onClick = { navigateToNew() },
                containerColor = LightGreen,
                modifier = Modifier.size(70.dp)
            ) {
                Text("+", fontSize = 30.sp, color = Color.White)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navigateBack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = "Irrigation",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

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
                                    onClick = { navigateToEdit(entry) },
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
