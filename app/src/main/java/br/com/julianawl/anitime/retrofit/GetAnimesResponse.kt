package br.com.julianawl.anitime.retrofit

import br.com.julianawl.anitime.model.Anime
import com.google.gson.annotations.SerializedName

data class GetAnimesResponse(
    @SerializedName("top") val animes: List<Anime>)
