package com.example.agrocontrolv1.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agrocontrolv1.R

data class HomeSection(val title: String, val imageRes: Int, val onClick: () -> Unit)

@Composable
fun HomeScreen(navigateTo: (String) -> Unit) {
    val sections = listOf(
        HomeSection("Fumigation", R.drawable.fumigation_image) {
            navigateTo("fumigation")
        },
        HomeSection("Products", R.drawable.products_image) {
            navigateTo("products")
        },
        HomeSection("Irrigation", R.drawable.irrigation_image) {
            navigateTo("irrigation")
        },
        HomeSection("Harvest", R.drawable.harvest_image) {
            navigateTo("harvest")
        },
        HomeSection("Finances", R.drawable.finances_image) {
            navigateTo("finances")
        },
        HomeSection("Workers", R.drawable.workers_image) {
            navigateTo("workers")
        },

    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        sections.forEach { section ->
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { section.onClick() }
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = section.imageRes),
                        contentDescription = section.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(section.title, fontSize = 18.sp, modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}
