package com.example.agrocontrolv1.finances.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class FinanceEntry(
    val date: String,
    val totalCost: String,
    val totalRevenue: String,
    val profit: String
)

@Composable
fun FinancesListScreen(
    navigateBack: () -> Unit
) {
    val finances = remember {
        mutableStateListOf(
            FinanceEntry("2023-03-15", "262.5", "100", "$$$"),
            FinanceEntry("2023-04-11", "187.5", "100", "$$$"),
            FinanceEntry("2023-06-12", "420", "100", "$$$"),
            FinanceEntry("2023-02-10", "337.5", "100", "$$$"),
            FinanceEntry("2023-01-05", "487.5", "100", "$$$"),
        )
    }

    // Variables para agregar nuevo
    var showDialog by remember { mutableStateOf(false) }
    var newDate by remember { mutableStateOf("") }
    var newCost by remember { mutableStateOf("") }
    var newRevenue by remember { mutableStateOf("") }

    // Variables para editar
    var showEditDialog by remember { mutableStateOf(false) }
    var editIndex by remember { mutableStateOf(-1) }
    var editDate by remember { mutableStateOf("") }
    var editCost by remember { mutableStateOf("") }
    var editRevenue by remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = Color(0xFF4CAF50)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = navigateBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text("Finances", fontSize = 24.sp)
            }

            Text(
                "Track your harvest finances. Monitor your sales and profits over time to manage the profitability of your farm.",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(finances.size) { index ->
                    val entry = finances[index]
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Date: ${entry.date}")
                                Text("Cost: ${entry.totalCost}")
                                Text("Revenue: ${entry.totalRevenue}")
                                Text("Profit: ${entry.profit}")
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                IconButton(onClick = {

                                    editIndex = index
                                    editDate = entry.date
                                    editCost = entry.totalCost
                                    editRevenue = entry.totalRevenue
                                    showEditDialog = true
                                }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                                }
                                IconButton(onClick = { finances.removeAt(index) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                                }
                            }
                        }
                    }
                }
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        finances.add(
                            FinanceEntry(newDate, newCost, newRevenue, "$$$")
                        )
                        newDate = ""
                        newCost = ""
                        newRevenue = ""
                        showDialog = false
                    }) {
                        Text("Add")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                },
                title = { Text("Add Finance Entry") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = newDate,
                            onValueChange = { newDate = it },
                            label = { Text("Date (yyyy-MM-dd)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = newCost,
                            onValueChange = { newCost = it },
                            label = { Text("Total Cost") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = newRevenue,
                            onValueChange = { newRevenue = it },
                            label = { Text("Total Revenue") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            )
        }

        if (showEditDialog) {
            AlertDialog(
                onDismissRequest = { showEditDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        if (editIndex >= 0) {
                            finances[editIndex] = FinanceEntry(
                                date = editDate,
                                totalCost = editCost,
                                totalRevenue = editRevenue,
                                profit = "$$$"
                            )
                        }
                        showEditDialog = false
                    }) {
                        Text("Save")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showEditDialog = false }) {
                        Text("Cancel")
                    }
                },
                title = { Text("Edit Finance Entry") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = editDate,
                            onValueChange = { editDate = it },
                            label = { Text("Date (yyyy-MM-dd)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = editCost,
                            onValueChange = { editCost = it },
                            label = { Text("Total Cost") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = editRevenue,
                            onValueChange = { editRevenue = it },
                            label = { Text("Total Revenue") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            )
        }
    }
}
