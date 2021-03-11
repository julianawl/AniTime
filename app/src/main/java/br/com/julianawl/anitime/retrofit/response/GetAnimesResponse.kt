package br.com.julianawl.anitime.retrofit.response

import br.com.julianawl.anitime.model.AnimeItem
import com.google.gson.annotations.SerializedName

//classe pra receber a resposta da api do discover
data class GetAnimesResponse(
    @SerializedName("top") val animes: List<AnimeItem>
)
