package br.com.julianawl.anitime.model

import com.google.gson.annotations.SerializedName

data class Anime(
    @SerializedName("mal_id") val id: Long,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("title") val title: String,
    @SerializedName("type") val type: String,
    @SerializedName("episodes") val episodes: Int,
    @SerializedName("airing") val airing: Boolean
)
