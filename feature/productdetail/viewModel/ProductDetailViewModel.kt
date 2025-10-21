package com.example.inventorymgmt.feature.productdetail.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventorymgmt.core.database.entity.Item
import com.example.inventorymgmt.feature.item.domain.ItemRepository
import com.example.inventorymgmt.feature.productdetail.state.ProductDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductDetailViewModel (
    private val itemRepository: ItemRepository,
    private val itemId: Int
): ViewModel() {
    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadProductDetail()
    }

    private fun loadProductDetail() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }
            // fetch product detail from repository
            runCatching {
                itemRepository.getItem(itemId).first()
            }.onSuccess {item: Item? ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        item = item,
                        errorMessage = if (item == null) "Item not found" else null
                    )
                }
            }.onFailure { error: Throwable ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.message
                    )
                }
            }
        }
    }
}