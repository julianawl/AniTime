package br.com.julianawl.anitime.retrofit

import br.com.julianawl.anitime.model.AnimeItem
import com.google.gson.annotations.SerializedName

data class GetSearchResponse (
    @SerializedName("results") val results: List<AnimeItem>)