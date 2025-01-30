package com.jmdev.app.store.presentation.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmdev.app.store.model.CartItem
import com.jmdev.app.store.model.Product
import com.jmdev.app.store.repository.CartRepository
import com.jmdev.app.store.service.FakeStoreService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val service: FakeStoreService,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product

    fun getProduct(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _product.value = service.getProductById(productId).body()
        }
    }

    fun addToCart(item: CartItem) {
        cartRepository.addToCart(item)
    }
}