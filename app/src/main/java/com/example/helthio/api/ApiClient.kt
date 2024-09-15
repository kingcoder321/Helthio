package com.example.helthio.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://api.figma.com/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.figma.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val figmaApi: FigmaApi = getRetrofit().create(FigmaApi::class.java)
}
