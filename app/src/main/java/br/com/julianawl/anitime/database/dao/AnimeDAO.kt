package br.com.julianawl.anitime.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import br.com.julianawl.anitime.model.AnimeItem
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDAO {

    @Insert(onConflict = REPLACE)
    suspend fun saveCompleteList(anime: AnimeItem)

    @Insert(onConflict = REPLACE)
    suspend fun savePTWList(anime: AnimeItem)

    @Query("SELECT * FROM AnimeItem WHERE status = 1")
    fun buscaComplete(): Flow<List<AnimeItem>>

    @Query("SELECT * FROM AnimeItem WHERE status = 2")
    fun buscaPTW(): Flow<List<AnimeItem>>

    @Delete
    suspend fun deleteComplete(anime: AnimeItem)

    @Delete
    suspend fun deletePTW(anime: AnimeItem)
}