package com.idagio.artists

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.idagio.artists.ArtistsRepositoryTest.Companion.TERM_BA
import com.idagio.artists.ArtistsRepositoryTest.Companion.TERM_BAC
import com.idagio.artists.ArtistsRepositoryTest.Companion.TERM_BACH
import com.idagio.artists.ArtistsRepositoryTest.Companion.TERM_EMPTY
import com.idagio.artists.repository.ArtistsRepository
import com.idagio.artists.schedulers.TrampolineSchedulerProvider
import com.idagio.artists.view.ui.artist_list.MainActivityViewModel
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


/**
 *  MainActivityViewModelTest unit test,
 *
 */
class MainActivityViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

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
        mainActivityViewModel = MainActivityViewModel(mockRepository,
            TrampolineSchedulerProvider()
        )
    }

    @Test
    fun test_empty_search() {
        mainActivityViewModel.searchArtist(TERM_EMPTY)
        Thread.sleep(1000)
        Assert.assertEquals(mainActivityViewModel.persons.value, null)
    }

    @Test
    fun test_search_with_two_characters() {
        mainActivityViewModel.searchArtist(TERM_BA)
        Thread.sleep(1000)
        Assert.assertEquals(mainActivityViewModel.persons.value, null)
    }

    @Test
    fun test_valid_search() {
        mainActivityViewModel.searchArtist(TERM_BACH)
        Thread.sleep(1000)
        Assert.assertEquals(
            mainActivityViewModel.persons.value,
            artistResultWrapperFactory.artistsResultWrapperWithTwoPersons().artists.persons
        )

    }

    @Test
    fun test_search_debounce() {
        mainActivityViewModel.searchArtist(TERM_BAC)
        Thread.sleep(200)
        mainActivityViewModel.searchArtist(TERM_BACH)
        Thread.sleep(1000)
        Assert.assertEquals(
            mainActivityViewModel.persons.value,
            artistResultWrapperFactory.artistsResultWrapperWithTwoPersons().artists.persons
        )
    }



}