package com.idagio.artists.di

import androidx.lifecycle.ViewModelProvider
import com.idagio.artists.repository.ArtistsDataSource
import com.idagio.artists.schedulers.BaseSchedulerProvider
import com.idagio.artists.schedulers.SchedulerProvider
import com.idagio.artists.view.ViewModelFactory
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module(subcomponents = [ViewModelSubComponent::class])
internal class AppModule {
    @Singleton
    @Provides
    fun provideArtistsDataSource(): ArtistsDataSource {

        return Retrofit.Builder()
            .baseUrl(ArtistsDataSource.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ArtistsDataSource::class.java)
    }

    @Singleton
    @Provides
    fun provideViewModelFactory(
        viewModelSubComponent: ViewModelSubComponent.Builder
    ): ViewModelProvider.Factory {

        return ViewModelFactory(viewModelSubComponent.build())
    }

    @Provides
    fun providesSchedulerProvider(): BaseSchedulerProvider {
        return SchedulerProvider()
    }
}
