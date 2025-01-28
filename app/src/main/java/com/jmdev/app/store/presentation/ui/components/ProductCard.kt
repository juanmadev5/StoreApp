package com.jmdev.app.store.presentation.ui.components

import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.jmdev.app.store.model.Product

@Composable
fun ProductCard(product: Product) {
    Card() {
        Text(product.title)
    }
}