package com.example.inventorymgmt.nav

import androidx.navigation3.runtime.NavKey
import com.example.inventorymgmt.core.database.entity.Category
import kotlinx.serialization.Serializable

interface AppNavKey: NavKey
@Serializable
object Home: AppNavKey
@Serializable
data class ItemList(val category: Category): AppNavKey
@Serializable
data class ProductDetail(val itemId: Int): AppNavKey
@Serializable
object Settings: AppNavKey
@Serializable
object Login: AppNavKey
