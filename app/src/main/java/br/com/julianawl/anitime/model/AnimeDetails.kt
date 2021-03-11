package br.com.julianawl.anitime.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//classe que pega as informações dos animes detalhadas

@Entity(tableName = "AnimeDetails")
data class AnimeDetails(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("mal_id") val id: Long,
    @SerializedName("image_url") val imageUrl: String,
    val title: String,
    val type: String,
    val episodes: Int?,
    @SerializedName("aired") val date: AiredDate?,
    val score: Float,
    val synopsis: String,
    @SerializedName("studios") val studio: List<Studios>
)
