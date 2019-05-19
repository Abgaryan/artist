package com.idagio.artists.repository


import com.idagio.artists.model.ArtistsResultWrapper
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface ArtistsDataSource {
    companion object {
        const val BASE_URL = "https://api.idagio.com/v1.8/lucene/"
    }

    @GET("search")
    fun searchArtists(@Query("term") baseRate: String): Single<ArtistsResultWrapper>

}