package com.idagio.artists.di;


import com.idagio.artists.view.ui.artist_list.MainActivityViewModel;
import dagger.Subcomponent;

/**
 * A sub component to create ViewModels. It is called by the
 * {@link com.idagio.artists.view.ui }.
 */
@Subcomponent
public interface ViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        ViewModelSubComponent build();
    }

    MainActivityViewModel mainActivityViewModel();



}
