package br.com.julianawl.anitime.repository

import br.com.julianawl.anitime.database.AnimeDAO
import br.com.julianawl.anitime.model.AnimeDetails
import br.com.julianawl.anitime.model.AnimeItem
import br.com.julianawl.anitime.retrofit.Api
import br.com.julianawl.anitime.retrofit.AppRetrofit
import br.com.julianawl.anitime.retrofit.GetAnimesResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class AnimesRepository(
    private val dao: AnimeDAO,
    private val api: Api = AppRetrofit().animeService
) {

    val allCompleteAnimes: Flow<List<AnimeItem>> = dao.buscaComplete()
    val allPTWAnimes: Flow<List<AnimeItem>> = dao.buscaPTW()

    suspend fun getTopAnimesAiring()
    : Response<GetAnimesResponse>{
        return api.getDiscover()
    }

    suspend fun getAnimeDetails(id: Long)
    : Response<AnimeDetails>{
        return api.getDetails(id)
    }

    suspend fun saveCompleteList(anime: AnimeItem) {
        anime.status = 1
        dao.saveCompleteList(anime)
    }

    suspend fun savePTWList(anime: AnimeItem){
        anime.status = 2
        dao.savePTWList(anime)
    }

}
