package com.jmdev.app.store.service

import com.jmdev.app.store.model.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface FakeStoreService {

    @GET("products?limit=10")
    suspend fun getProducts(): List<Product>

    @GET("products/categories")
    suspend fun getCategories(): List<String>

    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): List<Product>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product
}