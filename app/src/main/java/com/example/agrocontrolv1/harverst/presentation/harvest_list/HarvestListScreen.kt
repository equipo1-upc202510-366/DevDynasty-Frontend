package com.example.agrocontrolv1.harvest.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class HarvestEntry(val name: String, val date: String, val weight: Int, val price: Double)

@Composable
fun HarvestListScreen(navigateBack: () -> Unit, onAddHarvest: (HarvestEntry) -> Unit) {
    val harvestList = remember {
        mutableStateListOf(
            HarvestEntry("Maiz", "2023-03-15", 35, 7.5),
            HarvestEntry("Soja", "2023-04-11", 25, 7.5),
            HarvestEntry("Trigo", "2023-06-12", 56, 7.5),
            HarvestEntry("Alfalfa", "2023-02-10", 45, 7.5),
            HarvestEntry("Girasol", "2023-01-05", 65, 7.5)
        )
    }

    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }

    // Campos para agregar
    var newName by remember { mutableStateOf("") }
    var newDate by remember { mutableStateOf("") }
    var newWeight by remember { mutableStateOf("") }
    var newPrice by remember { mutableStateOf("") }

    // Campos para editar
    var editIndex by remember { mutableStateOf(-1) }
    var editName by remember { mutableStateOf("") }
    var editDate by remember { mutableStateOf("") }
    var editWeight by remember { mutableStateOf("") }
    var editPrice by remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = Color(0xFF4CAF50)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = navigateBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = "Harvest",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
            }
            Text(
                text = "Track your sold harvest weight and earnings over time to manage your farm's profitability.",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                itemsIndexed(harvestList) { index, entry ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text("Name: ${entry.name}")
                                    Text("Date: ${entry.date}")
                                    Text("Weight/Sale: ${entry.weight}")
                                    Text("Price per Weight: ${entry.price}")
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    IconButton(onClick = {
                                        editIndex = index
                                        editName = entry.name
                                        editDate = entry.date
                                        editWeight = entry.weight.toString()
                                        editPrice = entry.price.toString()
                                        showEditDialog = true
                                    }) {
                                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.Black)
                                    }
                                    IconButton(onClick = { harvestList.removeAt(index) }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Diálogo para agregar
    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    if (newName.isNotBlank() && newDate.isNotBlank() && newWeight.isNotBlank() && newPrice.isNotBlank()) {
                        harvestList.add(
                            HarvestEntry(
                                newName,
                                newDate,
                                newWeight.toIntOrNull() ?: 0,
                                newPrice.toDoubleOrNull() ?: 0.0
                            )
                        )
                        newName = ""
                        newDate = ""
                        newWeight = ""
                        newPrice = ""
                        showAddDialog = false
                    }
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancel")
                }
            },
            title = { Text("Add Harvest Entry") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = newName, onValueChange = { newName = it }, label = { Text("Name") })
                    OutlinedTextField(value = newDate, onValueChange = { newDate = it }, label = { Text("Sowing Date") })
                    OutlinedTextField(value = newWeight, onValueChange = { newWeight = it }, label = { Text("Weight/Sale") })
                    OutlinedTextField(value = newPrice, onValueChange = { newPrice = it }, label = { Text("Price per Weight") })
                }
            }
        )
    }

    // Diálogo para editar
    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    if (editIndex >= 0 && editName.isNotBlank() && editDate.isNotBlank()) {
                        harvestList[editIndex] = HarvestEntry(
                            editName,
                            editDate,
                            editWeight.toIntOrNull() ?: 0,
                            editPrice.toDoubleOrNull() ?: 0.0
                        )
                        showEditDialog = false
                    }
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("Cancel")
                }
            },
            title = { Text("Edit Harvest Entry") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = editName, onValueChange = { editName = it }, label = { Text("Name") })
                    OutlinedTextField(value = editDate, onValueChange = { editDate = it }, label = { Text("Sowing Date") })
                    OutlinedTextField(value = editWeight, onValueChange = { editWeight = it }, label = { Text("Weight/Sale") })
                    OutlinedTextField(value = editPrice, onValueChange = { editPrice = it }, label = { Text("Price per Weight") })
                }
            }
        )
    }
}
