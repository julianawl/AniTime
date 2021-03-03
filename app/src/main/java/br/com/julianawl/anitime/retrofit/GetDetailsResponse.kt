package br.com.julianawl.anitime.retrofit

import br.com.julianawl.anitime.model.AiredDate
import com.google.gson.annotations.SerializedName

data class GetDetailsResponse(
    @SerializedName("mal_id") val id: Long,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("title") val title: String,
    @SerializedName("title_english") val titleEnglish: String,
    @SerializedName("type") val type: String,
    @SerializedName("episodes") val episodes: Int,
    @SerializedName("aired") val date: AiredDate,
    @SerializedName("score") val score: Float,
    @SerializedName("synopsis") val synopsis: String
)
