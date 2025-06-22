package com.example.agrocontrolv1.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agrocontrolv1.R

data class HomeSection(val title: String, val imageRes: Int, val onClick: () -> Unit)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navigateTo: (String) -> Unit) {
    val sections = listOf(
        HomeSection("Fumigation", R.drawable.fumigation_image) { navigateTo("fumigation") },
        HomeSection("Products", R.drawable.products_image) { navigateTo("products") },
        HomeSection("Irrigation", R.drawable.irrigation_image) { navigateTo("irrigation") },
        HomeSection("Harvest", R.drawable.harvest_image) { navigateTo("harvest") },
        HomeSection("Finances", R.drawable.finances_image) { navigateTo("finances") },
        HomeSection("Workers", R.drawable.workers_image) { navigateTo("workers") },
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        buildAnnotatedString {
                            withStyle(SpanStyle(color = Color.White)) { append("Agro") }
                            withStyle(SpanStyle(color = Color(0xFF4CAF50))) { append("Control") }
                        },
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF00332E))
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(sections.size) { index ->
                val section = sections[index]
                Card(
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clickable { section.onClick() }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        Image(
                            painter = painterResource(id = section.imageRes),
                            contentDescription = section.title,
                            modifier = Modifier
                                .width(120.dp)
                                .height(100.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                        Text(
                            text = section.title,
                            fontSize = 20.sp,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.weight(1f) // ocupa el resto de espacio
                        )
                    }
                }
            }
        }
    }
}
