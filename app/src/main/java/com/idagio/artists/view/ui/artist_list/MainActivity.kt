package com.idagio.artists.view.ui.artist_list

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.idagio.artists.R
import com.idagio.artists.view.ui.adapter.RecyclerViewAdapter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    public lateinit var viewModelFactory: ViewModelProvider.Factory

    public lateinit var mainActivityViewModel: MainActivityViewModel


    private lateinit var recyclerViewAdapter: RecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)

        mainActivityViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MainActivityViewModel::class.java)

        initSearchView()
        initRecyclerView()
        observeViewModel()

    }

    private fun initSearchView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            search_query.requestFocus()
        }

        search_query.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                mainActivityViewModel.searchArtist(p0)
                return true
            }

        })
    }

    private fun initRecyclerView() {
        recyclerViewAdapter = RecyclerViewAdapter()
        recycler_view.adapter = recyclerViewAdapter
    }


    private fun observeViewModel() {
        mainActivityViewModel.loading.observe(this, Observer {
            if (it != null) {
                progressBar.visibility = if (it == true) View.VISIBLE else View.GONE
            }
        })


        mainActivityViewModel.persons.observe(this, Observer {
            if (it != null) {
                recyclerViewAdapter.setPersons(it)
            }
        })

    }


}


