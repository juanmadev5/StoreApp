package com.jmdev.app.store.presentation.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.jmdev.app.store.presentation.ui.components.ProductCard

@Composable
fun HomeScreen(navDetail: () -> Unit) {
    val viewModel: HomeViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.onCreate()
    }

    val products by viewModel.products.collectAsState()
    val categories by viewModel.categories.collectAsState()


    val scroll = rememberScrollState()

    Column (modifier = Modifier.fillMaxSize().verticalScroll(scroll)) {
        products.forEach {
            ProductCard(it)
        }
    }

}