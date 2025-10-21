package com.example.inventorymgmt.feature.item.domain

import com.example.inventorymgmt.core.database.entity.Category
import com.example.inventorymgmt.core.database.entity.Item
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    suspend fun insertItem(item: Item): Result<Unit>
    suspend fun updateItem(item: Item): Result<Unit>
    suspend fun deleteItem(item: Item): Result<Unit>
    fun getItem(id: Int): Flow<Item>
    fun getAllItems(): Flow<List<Item>>
    fun getProductCategories(): List<Category>
}