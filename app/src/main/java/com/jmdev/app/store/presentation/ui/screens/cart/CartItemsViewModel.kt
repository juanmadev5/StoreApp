package com.jmdev.app.store.presentation.ui.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmdev.app.store.model.CartItem
import com.jmdev.app.store.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CartItemsViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    val cartItems: StateFlow<List<CartItem>> = cartRepository.cartItems
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun removeFromCart(item: CartItem) {
        cartRepository.removeFromCart(item)
    }
}
