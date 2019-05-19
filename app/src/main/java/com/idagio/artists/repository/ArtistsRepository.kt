package com.idagio.artists.repository

import com.idagio.artists.model.ArtistsResultWrapper
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArtistsRepository @Inject
constructor(val dataSource: ArtistsDataSource) {


    fun  searchArtists(term:String): Single<ArtistsResultWrapper> {
        return dataSource.searchArtists(term)
    }



}