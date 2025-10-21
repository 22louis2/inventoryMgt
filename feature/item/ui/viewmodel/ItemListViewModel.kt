package com.example.inventorymgmt.feature.item.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventorymgmt.core.database.entity.Item
import com.example.inventorymgmt.feature.item.domain.ItemRepository
import com.example.inventorymgmt.feature.item.ui.state.ItemListUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemListViewModel(
    private val itemRepository: ItemRepository
): ViewModel() {
    fun deleteItem(item: Item) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                itemRepository.deleteItem(item)
            }
        }
    }

    val itemListUiState: StateFlow<ItemListUiState> = itemRepository.getAllItems()
        .flowOn(Dispatchers.IO)
        .distinctUntilChanged()
        .map {items -> ItemListUiState(items = items) }
        .onStart { emit(ItemListUiState()) }
        .catch { exception -> emit(ItemListUiState(errorMessage = exception.message)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ItemListUiState()
        )
}