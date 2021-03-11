package br.com.julianawl.anitime.model

import com.google.gson.annotations.SerializedName

//classe que pega a data de início e fim do lançamento do anime
data class AiredDate(
    @SerializedName("string") val completeDate: String
)
