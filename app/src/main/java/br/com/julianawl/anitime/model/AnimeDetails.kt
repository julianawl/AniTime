package br.com.julianawl.anitime.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "AnimeDetails")
data class AnimeDetails(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("mal_id") val id: Long,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("title") val title: String,
    @SerializedName("type") val type: String,
    @SerializedName("episodes") val episodes: Int,
    @SerializedName("aired") val date: AiredDate,
    @SerializedName("score") val score: Float,
    @SerializedName("synopsis") val synopsis: String,
    @SerializedName("studios") val studio: List<Studios>
)
