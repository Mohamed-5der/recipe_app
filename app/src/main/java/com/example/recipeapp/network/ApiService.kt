package com.example.recipeapp.network

import com.example.recipeapp.model.Food
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("categories.php")
    suspend fun getFood() :Response<Food>
}

object RetrofitInstance{
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    val gson = GsonBuilder().setLenient().create()
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
    val retrofit =Retrofit.Builder()
        .baseUrl("https://www.themealdb.com/api/json/v1/1/").
        addConverterFactory(GsonConverterFactory.
        create(gson)).
        client(okHttpClient).build()
    val apiService = retrofit.create(ApiService::class.java)

}