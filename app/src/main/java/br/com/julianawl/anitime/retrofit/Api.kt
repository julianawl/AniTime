package br.com.julianawl.anitime.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("top/anime/1/airing")
    suspend fun getDiscover() : Response<GetAnimesResponse>

    @GET("anime/{id}/")
    suspend fun getDetails(@Path("id") id: Long) : Response<GetDetailsResponse>

}