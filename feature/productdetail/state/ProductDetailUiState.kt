package com.example.inventorymgmt.feature.productdetail.state

import com.example.inventorymgmt.core.database.entity.Item

data class ProductDetailUiState(
    val item: Item? = null ,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

