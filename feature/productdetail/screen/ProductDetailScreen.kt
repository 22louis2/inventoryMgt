package com.example.inventorymgmt.feature.productdetail.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventorymgmt.core.database.InventoryDatabase
import com.example.inventorymgmt.feature.item.data.ItemRepositoryImpl
import com.example.inventorymgmt.feature.productdetail.viewModel.ProductDetailViewModel

@Composable
fun ProductDetailScreen(modifier: Modifier = Modifier, itemId: Int) {
    val context = LocalContext.current
    val applicationContext = context.applicationContext
    val database: InventoryDatabase = remember(applicationContext) {
        InventoryDatabase.getDatabase(applicationContext)
    }
    val itemDao = remember { database.itemDao() }

    val viewModel: ProductDetailViewModel = viewModel {
        ProductDetailViewModel(ItemRepositoryImpl(itemDao), itemId)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column {
        ListItem(
            headlineContent = {
                Text(text = uiState.item?.name ?: "Loading...")
            },
            supportingContent = {
                Text(text = uiState.item?.category.toString()) // No description field
            },
            trailingContent = {
                Text(text = "$${uiState.item?.price ?: "Loading..."}")
            }
        )
    }
}