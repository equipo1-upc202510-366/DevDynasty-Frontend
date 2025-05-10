package com.example.agrocontrolv1.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    val previousId: String,
    @ColumnInfo
    val name: String,
    @ColumnInfo
    val quantityPerUnit: String,
    @ColumnInfo
    val unitPrice: Double,
    @ColumnInfo
    val quantity: Int,
    @ColumnInfo
    val photoUrl: String
)
