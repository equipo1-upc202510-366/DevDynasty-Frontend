package com.example.agrocontrolv1.domain.model

import com.google.gson.annotations.SerializedName // Importa esto

data class Product(
    @SerializedName("id") // Añade esta anotación para mapear el campo "id" del JSON
    val previousId: String, // O podrías simplemente cambiar el nombre a 'id: String' si prefieres
    val name: String,
    val quantityPerUnit: String,
    val unitPrice: Double, // Confirma si tu backend devuelve un Double real o si necesitas manejarlo como Int/Long y convertir
    val quantity: Int,
    val photoUrl: String
)
// La función toProduct() sigue igual si mantienes previousId.
// Si cambiaste a 'id', ajusta toProduct() también.