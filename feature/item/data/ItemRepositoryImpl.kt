package com.example.inventorymgmt.feature.item.data

import com.example.inventorymgmt.core.database.dao.ItemDao
import com.example.inventorymgmt.core.database.entity.Category
import com.example.inventorymgmt.core.database.entity.Item
import com.example.inventorymgmt.feature.item.domain.ItemRepository
import kotlinx.coroutines.flow.Flow

class ItemRepositoryImpl(
    private val itemDao: ItemDao
): ItemRepository {
    override suspend fun insertItem(item: Item): Result<Unit> = runCatching { itemDao.insertItem(item) }

    override suspend fun updateItem(item: Item): Result<Unit> = runCatching { itemDao.updateItem(item) }

    override suspend fun deleteItem(item: Item): Result<Unit> = runCatching { itemDao.deleteItem(item) }

    override fun getItem(id: Int): Flow<Item> = itemDao.getItem(id)

    override fun getAllItems(): Flow<List<Item>> = itemDao.getAllItems()

    override fun getProductCategories(): List<Category> = Category.entries.toList()
}