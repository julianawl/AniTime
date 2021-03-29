package br.com.julianawl.anitime.retrofit

import br.com.julianawl.anitime.model.AnimeDetails
import br.com.julianawl.anitime.retrofit.response.GetAnimesResponse
import br.com.julianawl.anitime.retrofit.response.GetSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//endpoints da api
interface Api {

    @GET("top/anime/{page}/airing")
    suspend fun getDiscover(@Path("page") page: Int)
            : Response<GetAnimesResponse>

    @GET("anime/{id}/")
    suspend fun getDetails(@Path("id") id: Long)
            : Response<AnimeDetails>

    @GET("search/anime")
    suspend fun getAnimesSearch(
        @Query("q") keyword: String
    ): Response<GetSearchResponse>

}