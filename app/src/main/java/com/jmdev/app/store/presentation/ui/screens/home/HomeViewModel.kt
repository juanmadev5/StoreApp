package com.jmdev.app.store.presentation.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmdev.app.store.model.Product
import com.jmdev.app.store.service.FakeStoreService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val service: FakeStoreService
) : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products
        .onStart {
            onCreate()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories
        .onStart {
            onCreate()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyList()
        )

    private fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            _products.value = service.getProducts().body().orEmpty()
        }
    }

    private fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _categories.value = service.getCategories().body().orEmpty()
        }
    }

    private fun onCreate() {
        getProducts()
        getCategories()
    }
}