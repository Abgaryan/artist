package com.idagio.artists.view.ui.artist_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.idagio.artists.model.Person
import com.idagio.artists.repository.ArtistsRepository
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MainActivityViewModel @Inject
constructor(val artistsRepository: ArtistsRepository) : ViewModel() {

    private val disposable: CompositeDisposable = CompositeDisposable()

    val loading = MutableLiveData<Boolean>()
    val persons = MutableLiveData<List<Person>>()


    private lateinit var searchObservableEmitter: ObservableEmitter<String?>

    init {
        disposable.add(Observable.create(
            ObservableOnSubscribe<String> { subscriber ->
                searchObservableEmitter = subscriber
            })
            .map { text -> text.toLowerCase().trim() }
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter { text -> text.length > 2 }
            .subscribe { text ->
                loadArtists(text)
            })
    }


    fun searchArtist(term: String?) {
        searchObservableEmitter.onNext(term ?: "")
    }


    private fun loadArtists(term: String) {
        loading.postValue(true)
        disposable.add(
            artistsRepository.searchArtists(term).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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