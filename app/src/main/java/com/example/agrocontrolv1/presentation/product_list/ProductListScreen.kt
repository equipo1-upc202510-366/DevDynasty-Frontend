package com.example.agrocontrolv1.presentation.product_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer // Necesario para el espacio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height // Necesario para el espacio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color // Necesario para el color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight // Opcional: para negrita en el título
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp // Opcional: para tamaño de fuente en el título
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ProductListScreen(viewModel: ProductListViewModel) {
    val productIdInput = viewModel.productIdInput.value
    val state = viewModel.state.value

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth(), // Asegura que la columna ocupe todo el ancho
            horizontalAlignment = Alignment.CenterHorizontally // Centra los elementos hijos horizontalmente
        ) {
            // >>> Título "Products" <<<
            Text(
                text = "Products",
                fontSize = 28.sp, // Tamaño de fuente para el título (ejemplo)
                fontWeight = FontWeight.Bold, // Negrita para el título (ejemplo)
                color = Color.Green, // Color verde para el título
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp) // Espacio superior e inferior para el título
            )

            // Espacio visual entre el título y la casilla de búsqueda
            Spacer(modifier = Modifier.height(8.dp))

            // >>> Casilla de búsqueda (OutlinedTextField) <<<
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp), // Padding horizontal para que no llegue a los bordes
                value = productIdInput,
                onValueChange = {
                    viewModel.onProductIdInputChanged(it)
                },
                label = { Text("Enter Product ID") } // Texto de guía dentro del campo
            )

            // Espacio visual entre la casilla y el botón
            Spacer(modifier = Modifier.height(8.dp))


            // >>> Botón de búsqueda <<<
            OutlinedButton(onClick = {
                viewModel.searchProductById()
            }) {
                Text("Search Product by ID") // Texto del botón
            }

            // Espacio visual entre el botón y la lista/indicadores
            Spacer(modifier = Modifier.height(16.dp))


            // >>> Indicador de carga (si isLoading es true) <<<
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }

            // >>> Mostrar mensaje de error (si existe) <<<
            state.message?.let { message ->
                Text(
                    text = message,
                    color = Color.Red, // Color rojo para mensajes de error
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            // >>> Mostrar la lista de productos (si hay datos) <<<
            state.data?.let { products ->
                // Mensaje si no se encuentran productos
                if (products.isEmpty() && !state.isLoading && state.message == null && productIdInput.isNotBlank()) {
                    Text("No products found for this ID.", modifier = Modifier.padding(16.dp))
                }

                // LazyColumn para la lista desplazable
                LazyColumn(
                    // Ocupa todo el ancho restante y el espacio vertical disponible
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp) // Padding horizontal para la lista de tarjetas
                ) {
                    items(products) { product -> // Itera sobre cada producto en la lista
                        Card( // Tarjeta para cada producto
                            modifier = Modifier
                                .fillMaxWidth() // Tarjeta ocupa todo el ancho
                                .padding(vertical = 4.dp) // Espacio vertical entre tarjetas
                        ) {
                            // Fila para el contenido de la tarjeta (Imagen + Detalles)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                // Imagen del producto (asumiendo product.photoUrl existe)
                                GlideImage(
                                    modifier = Modifier
                                        .size(96.dp) // Tamaño de la imagen
                                        .padding(8.dp), // Padding alrededor de la imagen
                                    imageModel = { product.photoUrl }, // URL de la imagen
                                    imageOptions = ImageOptions(
                                        contentScale = ContentScale.Crop,
                                        alignment = Alignment.Center
                                    )
                                )
                                // Columna para los detalles del texto del producto
                                Column(
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp, vertical = 8.dp) // Padding alrededor del texto
                                        .weight(1f) // Permite que la columna ocupe el espacio restante en la fila
                                ) {
                                    // Mostrar el nombre del producto (asumiendo product.name existe)
                                    Text(
                                        text = product.name,
                                        fontWeight = FontWeight.Medium // Ejemplo de estilo para el nombre
                                    )
                                    // Mostrar el ID del producto

                                    // Si tu objeto Product tiene otras propiedades relevantes como 'quantityPerUnit':
                                    // Text(text = product.quantityPerUnit)
                                }
                                // Opcional: Botón de favorito si se implementa
                                /*
                                IconButton(onClick = { /* Acción al hacer clic en favorito */ }) {
                                    Icon(
                                        imageVector = Icons.Filled.Favorite,
                                        contentDescription = "Favorite",
                                        tint = Color.Gray // Color por defecto
                                        // Si Product tiene isFavorite y quieres que cambie de color:
                                        // tint = if (product.isFavorite) Color.Red else Color.Gray
                                    )
                                }
                                */
                            }
                        }
                    }
                }
            }
        }
    }
}