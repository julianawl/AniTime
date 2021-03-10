package br.com.julianawl.anitime.retrofit

import br.com.julianawl.anitime.model.AnimeDetails
import br.com.julianawl.anitime.model.AnimeItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("top/anime/1/airing")
    suspend fun getDiscover()
    : Response<GetAnimesResponse>

    @GET("anime/{id}/")
    suspend fun getDetails(@Path("id") id: Long)
    : Response<AnimeDetails>

    @GET("search/anime")
    suspend fun getAnimesSearch(@Query("q") keyword: String)
    : Response<GetSearchResponse>

}