package com.jmdev.app.store.model

import androidx.compose.runtime.Stable

@Stable
data class CartItem(
    val productId: Int,
    val product: Product,
    var quantity: Int
)