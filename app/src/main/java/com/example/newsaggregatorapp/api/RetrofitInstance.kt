package com.example.newsaggregatorapp.service

import android.content.Context
import com.example.newsaggregatorapp.api.NewsApiService
import java.util.Properties
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private var apiService: NewsApiService? = null
    private var apiKey: String? = null

    fun getApiService(context: Context): NewsApiService {
        if (apiService == null) {
            val properties = Properties()
            try {
                val inputStream = context.assets.open("config.properties")
                properties.load(inputStream)
            } catch (e: Exception) {
                throw RuntimeException("Failed to load config.properties: ${e.message}")
            }

            val baseUrl = properties.getProperty("BASE_URL", "https://newsapi.org/v2/")
            apiKey = properties.getProperty("API_KEY", "")

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            apiService = retrofit.create(NewsApiService::class.java)
        }
        return apiService!!
    }

    fun getApiKey(): String {
        return apiKey ?: throw IllegalStateException("API Key not initialized")
    }
}
