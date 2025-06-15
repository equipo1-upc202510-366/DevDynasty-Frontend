package com.example.agrocontrolv1.workers.presentation.worker_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
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

val LightGreen = Color(0xFF4CAF50)
val BackgroundColor = Color(0xFFF9F9F9)

data class Worker(val name: String, val phone: String, val document: String)

@Composable
fun WorkerListScreen(navigateBack: () -> Unit, navigateToAdd: () -> Unit) {
    val workers = remember {
        mutableStateListOf(
            Worker("Lucía Fernández", "12345678", "Fumigation"),
            Worker("Carlos Méndez", "12345678", "Fumigation"),
            Worker("Valeria Gómez", "12345678", "Fumigation"),
            Worker("Diego Morales", "12345678", "Fertilization"),
            Worker("Ricardo Ortiz", "12345678", "Fertilization")
        )
    }

    var editingWorker by remember { mutableStateOf<Worker?>(null) }

    Scaffold(
        containerColor = BackgroundColor,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToAdd() },
                containerColor = LightGreen,
                modifier = Modifier.size(72.dp)
            ) {
                Text("+", fontSize = 32.sp, color = Color.White)
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
                IconButton(onClick = { navigateBack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Text(
                    text = "Workers",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(workers) { worker ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Name: ${worker.name}", fontWeight = FontWeight.Medium)
                                Text("Phone: ${worker.phone}")
                                Text("Document: ${worker.document}")
                            }
                            Row {
                                IconButton(onClick = { editingWorker = worker }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                                }
                                IconButton(onClick = { workers.remove(worker) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                                }
                            }
                        }
                    }
                }
            }
        }

        if (editingWorker != null) {
            EditWorkerDialog(
                worker = editingWorker!!,
                onDismiss = { editingWorker = null },
                onSave = { updated ->
                    val index = workers.indexOf(editingWorker)
                    if (index != -1) {
                        workers[index] = updated
                    }
                    editingWorker = null
                }
            )
        }
    }
}

@Composable
fun EditWorkerDialog(
    worker: Worker,
    onDismiss: () -> Unit,
    onSave: (Worker) -> Unit
) {
    var name by remember { mutableStateOf(worker.name) }
    var phone by remember { mutableStateOf(worker.phone) }
    var document by remember { mutableStateOf(worker.document) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onSave(Worker(name, phone, document))
            }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        title = {
            Text("Edit Worker")
        },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Teléfono") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = document,
                    onValueChange = { document = it },
                    label = { Text("Documento") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        shape = MaterialTheme.shapes.large,
        containerColor = Color(0xFFF4F0F8)
    )
}

