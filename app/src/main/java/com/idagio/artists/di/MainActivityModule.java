package com.idagio.artists.di;


import com.idagio.artists.view.ui.artist_list.MainActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector
    abstract MainActivity contributeCurrencyListActivity();


}
