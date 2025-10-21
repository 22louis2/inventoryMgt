package com.example.inventorymgmt.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventorymgmt.core.database.entity.Category
import com.example.inventorymgmt.feature.home.state.HomeUiState
import com.example.inventorymgmt.feature.item.domain.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val itemRepository: ItemRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            _uiState.update {
                // update isLoading status to true
                it.copy(isLoading = true)
            }

            // fetch categories from repository
            runCatching {
                itemRepository.getProductCategories()
            }.onSuccess { categories: List<Category> ->
                _uiState.update {
                    // update categories and isLoading status to false
                    it.copy(
                        categories = categories,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            }.onFailure { error: Throwable ->
                _uiState.update {
                    // update errorMessage and isLoading status to false
                    it.copy(
                        errorMessage = error.message,
                        isLoading = false
                    )
                }
            }
        }
    }
}