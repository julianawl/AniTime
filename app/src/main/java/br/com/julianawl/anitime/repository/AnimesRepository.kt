package br.com.julianawl.anitime.repository

import br.com.julianawl.anitime.retrofit.Api
import br.com.julianawl.anitime.retrofit.AppRetrofit
import br.com.julianawl.anitime.retrofit.GetAnimesResponse
import br.com.julianawl.anitime.retrofit.GetDetailsResponse
import retrofit2.Response

class AnimesRepository(
    private val api: Api = AppRetrofit().animeService,
) {

    suspend fun getTopAnimesAiring()
    : Response<GetAnimesResponse>{
        return api.getDiscover()
    }

    suspend fun getAnimeDetails(id: Long)
    : Response<GetDetailsResponse>{
        return api.getDetails(id)
    }


}
