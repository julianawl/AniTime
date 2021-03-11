package br.com.julianawl.anitime.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

//classe que pega as informações básicas dos animes dispostas em listas

@Entity(tableName = "AnimeItem")
data class AnimeItem(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("mal_id") val id: Long,
    @SerializedName("image_url") val imageUrl: String,
    val title: String,
    val episodes: Int?,
    @SerializedName("start_date") var startDate: String?,
    @SerializedName("end_date") var endDate: String?,
    val score: Float,
    var status: Int = 0
) : Serializable