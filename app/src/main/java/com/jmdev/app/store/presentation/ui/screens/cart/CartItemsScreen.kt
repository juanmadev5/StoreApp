package com.jmdev.app.store.presentation.ui.screens.cart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jmdev.app.store.presentation.ui.components.CartItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartItemsScreen(navBack: () -> Unit, navToDetail: (Int) -> Unit) {
    val viewModel: CartItemsViewModel = hiltViewModel()
    val items by viewModel.cartItems.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Cart")
                },
                navigationIcon = {
                    IconButton(onClick = { navBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back to home")
                    }
                }
            )
        },
        floatingActionButton = {
            if (items.isNotEmpty()) {
                Button(onClick = {}) {
                    Text("Checkout")
                }
            }
        }
    ) { innerPadding ->
        if (items.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                item {
                    Text(
                        "${items.size} items, total value: ${items.sumOf { it.product.price * it.quantity }}$",
                        modifier = Modifier.padding(12.dp)
                    )
                }
                items(items.size) {
                    CartItemCard(
                        item = items[it],
                        navToDetail = { product -> navToDetail(product) },
                        quantity = items[it].quantity,
                        remove = { thisItem ->
                            viewModel.removeFromCart(thisItem)
                        }
                    )
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Cart is empty")
            }
        }

    }
}