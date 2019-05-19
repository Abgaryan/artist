package com.idagio.artists

import com.idagio.artists.model.ArtistsResultWrapper
import com.idagio.artists.repository.ArtistsRepository
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 *  ArtistsRepository unit test,
 *
 */

class  ArtistsRepositoryTest {

    @Mock
    private lateinit var mockRepository: ArtistsRepository
    private val testObserver = TestObserver<ArtistsResultWrapper>()
    private  val  artistResultWrapperFactory = ArtistResultWrapperFactory()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(mockRepository.searchArtists(TERM_BACH)).thenReturn(
            Single.just(artistResultWrapperFactory.artistsResultWrapperWithTwoPersons())
        )
        Mockito.`when`(mockRepository.searchArtists(TERM_EMPTY)).thenReturn(
            Single.just(artistResultWrapperFactory.artistsResultWrapperWithoutPerson())
        )
    }

    @Test
    fun search_by_valid_artist_name() {
        val result = mockRepository.searchArtists(TERM_BACH)
        result.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        testObserver.assertResult(artistResultWrapperFactory.artistsResultWrapperWithTwoPersons())
    }

    @Test
    fun search_empty_artist_name() {
        val result = mockRepository.searchArtists(TERM_EMPTY)
        result.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        testObserver.assertResult(artistResultWrapperFactory.artistsResultWrapperWithoutPerson())
    }

    companion object {
        const val TERM_BACH = "bach"
        const val TERM_BAC = "bac"
        const val TERM_BA = "ba"
        const val TERM_EMPTY = ""
    }
}