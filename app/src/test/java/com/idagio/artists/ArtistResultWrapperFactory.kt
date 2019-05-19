package com.idagio.artists

import com.idagio.artists.model.Artists
import com.idagio.artists.model.ArtistsResultWrapper
import com.idagio.artists.model.Person

class ArtistResultWrapperFactory {

    fun artistsResultWrapperWithTwoPersons(): ArtistsResultWrapper = ArtistsResultWrapper(
        artists =
        Artists(
            persons = listOf(
                Person(id = 1, forename = "Franz", surname = "Bach"),
                Person(id = 1, forename = "Johann", surname = "Bach")
            )
        )

    )

    fun artistsResultWrapperWithoutPerson(): ArtistsResultWrapper = ArtistsResultWrapper(
        artists =
        Artists(
            persons = listOf(
            )
        )

    )

}