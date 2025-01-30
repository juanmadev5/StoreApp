package com.jmdev.app.store.repository

import com.jmdev.app.store.model.CartItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepository @Inject constructor() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    fun getCartItems(): List<CartItem> {
        return _cartItems.value
    }

    fun addToCart(item: CartItem) {
        _cartItems.update { currentCart ->
            val updatedCart = currentCart.toMutableList()
            val existingItemIndex = updatedCart.indexOfFirst { it.productId == item.productId }

            if (existingItemIndex != -1) {
                val existingItem = updatedCart[existingItemIndex]
                updatedCart[existingItemIndex] =
                    existingItem.copy(quantity = existingItem.quantity + item.quantity)
            } else {
                updatedCart.add(item)
            }

            updatedCart.toList()
        }
    }

    fun removeFromCart(item: CartItem) {
        _cartItems.update { currentCart ->
            currentCart.filter { it.productId != item.productId }
        }
    }
}