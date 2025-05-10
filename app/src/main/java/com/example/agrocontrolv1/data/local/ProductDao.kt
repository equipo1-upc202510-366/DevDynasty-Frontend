package com.example.agrocontrolv1.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface ProductDao {
    @Insert
    suspend fun insert(productEntity: ProductEntity)

    @Delete
    suspend fun delete(productEntity: ProductEntity)

    @Query("select * from products where previousId =:id")
    suspend fun fetchById(id: String): ProductEntity?
}