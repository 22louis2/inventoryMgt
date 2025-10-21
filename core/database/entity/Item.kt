package com.example.inventorymgmt.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    // val description: String,
    val quantity: Int,
    val price: Double,
    // val imageUrl: String
    val category: Category
)
