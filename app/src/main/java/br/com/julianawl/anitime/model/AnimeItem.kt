package br.com.julianawl.anitime.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "AnimeItem")
data class AnimeItem(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("mal_id") val id: Long,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("title") val title: String,
    @SerializedName("episodes") val episodes: Int?,
    @SerializedName("start_date") var startDate: String?,
    @SerializedName("end_date") var endDate: String?,
    @SerializedName("score") val score: Float
)