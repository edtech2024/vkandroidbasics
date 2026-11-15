package com.example.vk_android_basics.data.remote

import com.example.vkandroidbasics.data.model.AppDto
import kotlinx.coroutines.delay
import retrofit2.http.GET
import retrofit2.http.Path

interface AppsApi {

    @GET("catalog")
    suspend fun getCatalog(): List<AppDto>

    @GET("{id}")
    suspend fun getAppDetails(@Path("id") id: String): AppDto
}

suspend fun AppsApi.getRating(id: String): Float {
    delay(1500) // эмуляция сетевого запроса
    return (30..50).random() / 10f
}
