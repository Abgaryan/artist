package com.idagio.artists

import com.idagio.artists.ArtistsRepositoryTest.Companion.TERM_BA
import com.idagio.artists.ArtistsRepositoryTest.Companion.TERM_BAC
import com.idagio.artists.ArtistsRepositoryTest.Companion.TERM_BACH
import com.idagio.artists.ArtistsRepositoryTest.Companion.TERM_EMPTY
import com.idagio.artists.repository.ArtistsRepository
import com.idagio.artists.view.ui.artist_list.MainActivityViewModel

import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


/**
 *  MainActivityViewModelTest unit test,
 *
 */
class MainActivityViewModelTest {


    @Mock
    private lateinit var mockRepository: ArtistsRepository

    private lateinit var mainActivityViewModel: MainActivityViewModel

    private val artistResultWrapperFactory = ArtistResultWrapperFactory()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(mockRepository.searchArtists(TERM_BACH)).thenReturn(
            Single.just(artistResultWrapperFactory.artistsResultWrapperWithTwoPersons())
        )
        Mockito.`when`(mockRepository.searchArtists(TERM_EMPTY)).thenReturn(
            Single.just(artistResultWrapperFactory.artistsResultWrapperWithoutPerson())
        )
        mainActivityViewModel = MainActivityViewModel(mockRepository)
    }

    @Test
    fun test_empty_search() {
        mainActivityViewModel.searchArtist(TERM_EMPTY)
        Assert.assertEquals(mainActivityViewModel.persons.value, null)
    }

    @Test
    fun test_search_with_two_characters() {
        mainActivityViewModel.searchArtist(TERM_BA)
        Assert.assertEquals(mainActivityViewModel.persons.value, null)
    }

    @Test
    fun test_valid_search() {
        mainActivityViewModel.searchArtist(TERM_BACH)
        Assert.assertEquals(
            mainActivityViewModel.persons.value,
            artistResultWrapperFactory.artistsResultWrapperWithTwoPersons().artists.persons
        )
    }

    @Test
    fun test_search_debounce() {
        mainActivityViewModel.searchArtist(TERM_BAC)
        mainActivityViewModel.searchArtist(TERM_BACH)
        Assert.assertEquals(
            mainActivityViewModel.persons.value,
            artistResultWrapperFactory.artistsResultWrapperWithTwoPersons().artists.persons
        )
    }



}