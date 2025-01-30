package com.jmdev.app.store.presentation.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jmdev.app.store.presentation.ui.components.ProductCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navDetail: (Int) -> Unit, navToCart: () -> Unit) {
    val viewModel: HomeViewModel = hiltViewModel()

    val products by viewModel.products.collectAsStateWithLifecycle()
    val categories by viewModel.categories.collectAsStateWithLifecycle()
    var currentCategory by rememberSaveable { mutableStateOf("all") }

    val gridScrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Store",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp
                    )
                },
                actions = {
                    IconButton(onClick = {
                        navToCart()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ShoppingCart,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                LazyRow(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    state = gridScrollState
                ) {
                    item {
                        AssistChip(
                            onClick = {
                                currentCategory = "all"
                                scope.launch {
                                    gridScrollState.animateScrollToItem(0)
                                }
                            },
                            label = {
                                Text("all")
                            },
                            leadingIcon = {
                                if(currentCategory == "all") {
                                    Icon(
                                        imageVector = Icons.Filled.Check,
                                        ""
                                    )
                                }
                            }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.width(8.dp))
                    }

                    items(categories.size) {
                        AssistChip(
                            onClick = {
                                currentCategory = categories[it]
                                scope.launch {
                                    gridScrollState.animateScrollToItem(it)
                                }
                            },
                            label = {
                                Text(categories[it])
                            },
                            leadingIcon = {
                                if(currentCategory == categories[it]) {
                                    Icon(
                                        imageVector = Icons.Filled.Check,
                                        ""
                                    )
                                }
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }

            if (currentCategory == "all") {
                items(count = products.size) {
                    ProductCard(products[it], navToDetail = { id ->
                        navDetail(id)
                    })
                }
            } else {
                products.filter { it.category == currentCategory }.forEach {
                    item {
                        ProductCard(it, navToDetail = { id ->
                            navDetail(id)
                        })
                    }
                }
            }
        }
    }
}