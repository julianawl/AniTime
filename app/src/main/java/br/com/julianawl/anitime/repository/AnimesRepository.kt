package br.com.julianawl.anitime.repository

import br.com.julianawl.anitime.database.dao.AnimeDAO
import br.com.julianawl.anitime.model.AnimeDetails
import br.com.julianawl.anitime.model.AnimeItem
import br.com.julianawl.anitime.retrofit.Api
import br.com.julianawl.anitime.retrofit.AppRetrofit
import br.com.julianawl.anitime.retrofit.response.GetAnimesResponse
import br.com.julianawl.anitime.retrofit.response.GetSearchResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class AnimesRepository(
    private val dao: AnimeDAO,
    private val api: Api = AppRetrofit().animeService
) {
    //pega todos os animes que estão nas listas Complete e PTW (mylist)
    val allCompleteAnimes: Flow<List<AnimeItem>> = dao.buscaComplete()
    val allPTWAnimes: Flow<List<AnimeItem>> = dao.buscaPTW()

    //pega todos os animes que estão lançando (discover)
    suspend fun getTopAnimesAiring(page: Int)
            : Response<GetAnimesResponse> {
        return api.getDiscover(page)
    }

    //pega os animes de acordo com a busca feita (discover)
    suspend fun getAnimesSearch(keyword: String)
                : Response<GetSearchResponse> {
            return api.getAnimesSearch(keyword)
    }

    //mostra os detalhes do anime selecionado (details)
    suspend fun getAnimeDetails(id: Long)
            : Response<AnimeDetails> {
        return api.getDetails(id)
    }

    //salva anime selecionado na lista de completos (details)
    suspend fun saveCompleteList(anime: AnimeItem) {
        anime.status = 1
        dao.saveCompleteList(anime)
    }

    //salva anime selecionado na lista que planeja assistir (details)
    suspend fun savePTWList(anime: AnimeItem) {
        anime.status = 2
        dao.savePTWList(anime)
    }

    //deleta animes na lista de completos (mylist)
    suspend fun deleteCompleteList(anime: AnimeItem) {
        dao.deleteComplete(anime)
    }

    //deleta animes na lista que planeja assistir (mylist)
    suspend fun deletePTWList(anime: AnimeItem) {
        dao.deletePTW(anime)
    }

}
