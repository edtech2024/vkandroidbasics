package com.example.vkandroidbasics.data.model

import com.google.gson.annotations.SerializedName

data class AppDto(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("category")
    val category: String? = null,
    @SerializedName("iconUrl")
    val iconUrl: String? = null
)