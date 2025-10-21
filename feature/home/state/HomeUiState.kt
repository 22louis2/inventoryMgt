package com.example.inventorymgmt.feature.home.state

import com.example.inventorymgmt.core.database.entity.Category

data class HomeUiState(
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
