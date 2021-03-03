package br.com.julianawl.anitime.model

import com.google.gson.annotations.SerializedName

data class AnimeDisc(
    @SerializedName("mal_id") val id: Long,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("title") val title: String,
    @SerializedName("type") val type: String,
    @SerializedName("episodes") val episodes: Int,
    @SerializedName("start_date") val startDate: String,
    @SerializedName("end_date") val endDate: String,
    @SerializedName("score") val score: Float
)