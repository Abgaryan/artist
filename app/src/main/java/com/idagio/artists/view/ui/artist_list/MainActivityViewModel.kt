package com.idagio.artists.view.ui.artist_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.idagio.artists.model.Person
import com.idagio.artists.repository.ArtistsRepository
import com.idagio.artists.schedulers.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MainActivityViewModel @Inject
constructor(val artistsRepository: ArtistsRepository, val schedulerProvider: BaseSchedulerProvider) : ViewModel() {

    private val disposable: CompositeDisposable = CompositeDisposable()
    private val subject = PublishSubject.create<String>()
    val loading = MutableLiveData<Boolean>()
    val persons = MutableLiveData<List<Person>>()

    init {
        disposable.add(
            subject
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.io())
                .map { text -> text.toLowerCase().trim() }
                .debounce(300, TimeUnit.MILLISECONDS)
                .filter { text -> text.length > 2 }
                .subscribe { text ->
                    loadArtists(text)
                })
    }


    fun searchArtist(term: String?) {
        subject.onNext(term ?: "")
    }


    private fun loadArtists(term: String) {
        loading.postValue(true)
        disposable.add(
            artistsRepository.searchArtists(term).subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .map { artistsResultWrapper -> artistsResultWrapper.artists.persons }
                .subscribeWith(object : DisposableSingleObserver<List<Person>>() {
                    override fun onSuccess(value: List<Person>) {
                        loading.value = false
                        persons.value = value
                    }

                    override fun onError(e: Throwable) {
                        loading.value = false
                    }
                })
        )

    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

}