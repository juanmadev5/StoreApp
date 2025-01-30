package com.jmdev.app.store.service

import com.jmdev.app.store.model.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FakeStoreService {

    @GET("products")
    suspend fun getProducts(): Response<List<Product>>

    @GET("products/categories")
    suspend fun getCategories(): Response<List<String>>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Response<Product>
}