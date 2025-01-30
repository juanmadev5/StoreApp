package com.jmdev.app.store.presentation.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.jmdev.app.store.model.CartItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(productId: Int, navBack: () -> Unit, navToCart: () -> Unit) {
    val viewModel: DetailViewModel = hiltViewModel()
    val product by viewModel.product.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val cartItems = rememberSaveable { mutableIntStateOf(1) }

    LaunchedEffect(Unit) {
        viewModel.getProduct(productId)
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text("Product detail")
                },
                navigationIcon = {
                    IconButton(onClick = { navBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back to home")
                    }
                },
                actions = {
                    IconButton(onClick = { navToCart() }) {
                        Icon(Icons.Filled.ShoppingCart, "View cart")
                    }
                }
            )
        },
        floatingActionButton = {
            if (product != null) {
                FloatingActionButton(onClick = {
                    scope.launch {
                        val productToAdd = CartItem(
                            productId = product!!.id,
                            product = product!!,
                            quantity = cartItems.intValue
                        )
                        viewModel.addToCart(productToAdd)
                        snackBarHostState.showSnackbar("Added to cart")
                    }
                }) {
                    Icon(Icons.Filled.AddShoppingCart, "Add to cart")
                }
            }
        }
    ) { innerPadding ->
        if (product != null) {
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .size(320.dp)
                            .background(Color.White)
                            .padding(12.dp), contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(product!!.image)
                                .crossfade(true)
                                .build(),
                            contentDescription = product!!.title,
                            modifier = Modifier.size(120.dp)
                        )
                    }
                }
                item {
                    Text(
                        "${product!!.price}$",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(12.dp)
                    )
                }
                item {
                    Text(
                        product!!.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(12.dp)
                    )
                }
                item {
                    Row {
                        Text(
                            "Add to cart: ${cartItems.intValue} item(s)",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(12.dp)
                        )
                        Row {
                            IconButton(onClick = {
                                if (cartItems.intValue > 1) {
                                    cartItems.intValue--
                                }
                            }) {
                                Icon(Icons.Filled.Remove, "Add to cart")
                            }
                            IconButton(onClick = { cartItems.intValue++ }) {
                                Icon(Icons.Filled.Add, "Add to cart")
                            }

                        }
                    }
                }
                item {
                    Text(
                        "Category: ${product!!.category}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(12.dp)
                    )
                }
                item {
                    Text(
                        product!!.description,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(12.dp)
                    )
                    Spacer(modifier = Modifier.padding(bottom = 64.dp)) // Add some space at the bottom to avoid overlap with the FAB
                }

            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.padding(8.dp))
                Text("Loading...")
            }
        }

    }
}