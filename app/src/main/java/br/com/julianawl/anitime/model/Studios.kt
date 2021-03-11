package br.com.julianawl.anitime.model

import com.google.gson.annotations.SerializedName

//classe para pegar os estúdios que participaram do anime escolhido
data class Studios(
    @SerializedName("name") val studioName: String
)
