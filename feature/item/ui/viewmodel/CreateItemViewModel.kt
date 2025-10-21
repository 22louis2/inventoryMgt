package com.example.inventorymgmt.feature.item.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventorymgmt.core.database.entity.Category
import com.example.inventorymgmt.core.database.entity.Item
import com.example.inventorymgmt.feature.item.domain.ItemRepository
import com.example.inventorymgmt.feature.item.ui.state.CreateItemUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateItemViewModel(
    private val itemRepository: ItemRepository,
    private val category: Category
): ViewModel() {
    private val _createItemUiState = MutableStateFlow(CreateItemUiState.Empty)
    val createItemUiState = _createItemUiState.asStateFlow()

    fun updateItemName(name: String) {
        _createItemUiState.update { it.copy(name = name,) }
    }

    fun updateItemPrice(price: String) {
        _createItemUiState.update { it.copy(price = price,) }
    }

    fun updateItemQuantity(quantity: String) {
        _createItemUiState.update { it.copy(quantity = quantity,) }
    }

    fun updateItemCategory(category: String) {
        _createItemUiState.update { it.copy(category = category,) }
    }

    fun setCurrentUiState(item: Item) {
        _createItemUiState.update {
            it.copy(
                id = item.id,
                name = item.name,
                price = item.price.toString(),
                quantity = item.quantity.toString(),
                category = item.category.toString()
            )
        }
    }

    fun insertItem() {
        viewModelScope.launch {
            _createItemUiState.update {
                it.copy(isLoading = true,)
            }

            val result = withContext(Dispatchers.IO) {
                itemRepository.insertItem(
                    Item(
                        name = createItemUiState.value.name,
                        price = createItemUiState.value.price.toDouble(),
                        quantity = createItemUiState.value.quantity.toInt(),
                        category = Category.valueOf(createItemUiState.value.category)
                    )
                )
            }

            result.onSuccess {
                _createItemUiState.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = true
                    )
                }
            }.onFailure {error: Throwable ->
                _createItemUiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.message,
                        isSuccess = false
                    )
                }
            }
        }
    }

    fun updateItem() {
        viewModelScope.launch {
            _createItemUiState.update {
                it.copy(isLoading = true,)
            }

            val result = withContext(Dispatchers.IO) {
                itemRepository.updateItem(
                    Item(
                        id = createItemUiState.value.id,
                        name = createItemUiState.value.name,
                        price = createItemUiState.value.price.toDouble(),
                        quantity = createItemUiState.value.quantity.toInt(),
                        category = Category.valueOf(createItemUiState.value.category)
                    )
                )
            }

            result.onSuccess {
                _createItemUiState.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = true
                    )
                }
            }.onFailure {error: Throwable ->
                _createItemUiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = error.message,
                        isSuccess = false
                    )
                }
            }
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            _createItemUiState.update { it.copy(isLoading = true) }
            val result = withContext(Dispatchers.IO) {
                itemRepository.deleteItem(item)
            }

            _createItemUiState.update {
                it.copy(isLoading = false, isSuccess = true)
            }
        }
    }


    fun resetCreateUiState() {
        _createItemUiState.update {
            CreateItemUiState.Empty
        }
    }
}