package br.com.julianawl.anitime.database

import androidx.room.TypeConverter
import br.com.julianawl.anitime.model.Studios
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


//estudando possibilidade de uso
class Converters {
    @TypeConverter
    fun studiosToJson(studios: List<Studios>?) = Gson().toJson(studios)

    inline fun <reified T> genericType() = object: TypeToken<T>() {}.type

    @TypeConverter
    fun jsonToStudios(json: String): List<Char>{
        val type = genericType<List<Studios>>()
        return Gson().toJson(json, type).toList()
    }
}