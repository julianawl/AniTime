package br.com.julianawl.anitime.retrofit.response

import br.com.julianawl.anitime.model.AnimeItem
import com.google.gson.annotations.SerializedName

//classe para receber a resposta da api da pesquisa
data class GetSearchResponse(
    @SerializedName("results") val results: MutableList<AnimeItem>
)