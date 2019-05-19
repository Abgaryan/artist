package com.idagio.artists.view


import androidx.collection.ArrayMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.idagio.artists.di.ViewModelSubComponent
import com.idagio.artists.view.ui.artist_list.MainActivityViewModel
import java.util.concurrent.Callable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject
constructor(viewModelSubComponent: ViewModelSubComponent) : ViewModelProvider.Factory {
    private val creators: ArrayMap<Class<*>, Callable<out ViewModel>> = ArrayMap<Class<*>, Callable<out ViewModel>>()

    init {

        // View models cannot be injected directly because they won't be bound to the owner's view model scope.
        creators[MainActivityViewModel::class.java] =
            Callable<ViewModel> { viewModelSubComponent.mainActivityViewModel() }
    }


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("Unknown model class $modelClass")
        }
        try {
            @Suppress("UNCHECKED_CAST")
            return creator.call() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}