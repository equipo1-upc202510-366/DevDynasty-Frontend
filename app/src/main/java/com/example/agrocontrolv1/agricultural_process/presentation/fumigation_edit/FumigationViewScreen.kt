@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.agrocontrolv1.agricultural_process.presentation.fumigation_edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class FumigationWorker(val name: String, val cost: String)
data class ProductEntry(var name: String, var cost: String)

@Composable
fun FumigationViewScreen(
    navigateBack: () -> Unit,
    isEditing: Boolean,
    initialDate: String? = null,
    initialHours: Int? = null
) {
    var isEditable by remember { mutableStateOf(!isEditing) }
    val productList = remember { mutableStateListOf(ProductEntry("Product 1", "0")) }

    var hours by remember { mutableStateOf(initialHours?.toString() ?: "") }
    val workers = remember { mutableStateListOf(FumigationWorker("Worker 1", "0")) }

    val formatter = DateTimeFormatter.ofPattern("dd 'of' MMMM yyyy")
    val parsedDate = initialDate?.let {
        LocalDate.parse(it, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = parsedDate?.atStartOfDay(ZoneId.systemDefault())
            ?.toInstant()?.toEpochMilli()
    )

    var showDatePicker by remember { mutableStateOf(false) }

    val selectedDate = datePickerState.selectedDateMillis?.let {
        Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        buildAnnotatedString {
                            withStyle(SpanStyle(color = Color.White)) { append("Fumigation ") }
                            withStyle(SpanStyle(color = Color(0xFF4CAF50))) { append("schedule") }
                        },
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF00332E))
            )
        },
        floatingActionButton = {
            if (selectedDate != null) {
                FloatingActionButton(
                    onClick = {
                        if (isEditable) {
                            navigateBack()
                        } else {
                            isEditable = true
                        }
                    },
                    containerColor = Color(0xFF4CAF50),
                    modifier = Modifier.size(60.dp)
                ) {
                    Icon(
                        if (isEditable) Icons.Default.Check else Icons.Default.Edit,
                        contentDescription = "Action",
                        tint = Color.White
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = isEditable) {
                        showDatePicker = true
                    }
            ) {
                OutlinedTextField(
                    value = selectedDate?.format(formatter) ?: "Select date",
                    onValueChange = {},
                    readOnly = true,
                    enabled = isEditable,
                    label = { Text("Date") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(onClick = { showDatePicker = false }) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDatePicker = false }) {
                            Text("Cancel")
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }

            if (selectedDate != null) {
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = hours,
                    onValueChange = { if (isEditable) hours = it },
                    label = { Text("Hours") },
                    enabled = isEditable,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    itemsIndexed(workers) { index, worker ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = worker.name,
                                onValueChange = {
                                    if (isEditable) workers[index] = worker.copy(name = it)
                                },
                                label = { Text("Worker") },
                                enabled = isEditable,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 8.dp)
                            )
                            OutlinedTextField(
                                value = worker.cost,
                                onValueChange = {
                                    if (isEditable) workers[index] = worker.copy(cost = it)
                                },
                                label = { Text("Cost") },
                                enabled = isEditable,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 8.dp)
                            )
                            if (isEditable) {
                                IconButton(onClick = {
                                    workers.add(FumigationWorker("Worker ${workers.size + 1}", "0"))
                                }) {
                                    Icon(Icons.Default.Add, contentDescription = "Add", tint = Color(0xFF4CAF50))
                                }
                                IconButton(onClick = {
                                    if (workers.size > 1) workers.removeAt(index)
                                }) {
                                    Icon(Icons.Default.Close, contentDescription = "Remove", tint = Color.Red)
                                }
                            }
                        }
                    }
                }


                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    itemsIndexed(productList) { index, product ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = product.name,
                                onValueChange = {
                                    if (isEditable) productList[index] = product.copy(name = it)
                                },
                                label = { Text("Worker") },
                                enabled = isEditable,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 8.dp)
                            )
                            OutlinedTextField(
                                value = product.cost,
                                onValueChange = {
                                    if (isEditable) productList[index] = product.copy(cost = it)
                                },
                                label = { Text("Cost") },
                                enabled = isEditable,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 8.dp)
                            )
                            if (isEditable) {
                                IconButton(onClick = {
                                    productList.add(ProductEntry("Product ${productList.size + 1}", "0"))
                                }) {
                                    Icon(Icons.Default.Add, contentDescription = "Add", tint = Color(0xFF4CAF50))
                                }
                                IconButton(onClick = {
                                    if (productList.size > 1) productList.removeAt(index)
                                }) {
                                    Icon(Icons.Default.Close, contentDescription = "Remove", tint = Color.Red)
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}
