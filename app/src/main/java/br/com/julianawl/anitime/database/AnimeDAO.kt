package br.com.julianawl.anitime.database

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import br.com.julianawl.anitime.model.AnimeItem
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDAO {

    @Insert(onConflict = REPLACE)
    suspend fun saveCompleteList(anime: AnimeItem)

    @Delete
    suspend fun deleteComplete(anime: AnimeItem)

    @Query("SELECT * FROM AnimeItem WHERE status = 1")
    fun buscaComplete(): Flow<List<AnimeItem>>

    @Insert(onConflict = REPLACE)
    suspend fun savePTWList(anime: AnimeItem)

    @Delete
    suspend fun deletePTW(anime: AnimeItem)

    @Query("SELECT * FROM AnimeItem WHERE status = 2")
    fun buscaPTW(): Flow<List<AnimeItem>>
}