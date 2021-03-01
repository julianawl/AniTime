package br.com.julianawl.anitime.retrofit

import retrofit2.Response
import retrofit2.http.GET

interface Api {

    @GET("top/anime/1/airing")
    suspend fun getDiscover() : Response<GetAnimesResponse>

}