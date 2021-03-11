package br.com.julianawl.anitime.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//instância do retrofit
private const val BASE_URL = "https://api.jikan.moe/v3/"

class AppRetrofit {

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor())
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
    val animeService: Api by lazy {
        retrofit.create(Api::class.java)
    }

}