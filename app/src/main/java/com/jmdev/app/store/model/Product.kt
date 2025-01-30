package com.jmdev.app.store.model

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class Product (
    val id: Int,
    val title: String,
    val price: Double,
    val category: String,
    val description: String,
    val image: String
)