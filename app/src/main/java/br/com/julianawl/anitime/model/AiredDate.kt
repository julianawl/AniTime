package br.com.julianawl.anitime.model

import com.google.gson.annotations.SerializedName

data class AiredDate(
    @SerializedName("string") val completeDate: String
)
