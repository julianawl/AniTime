package br.com.julianawl.anitime.ui.discover

import android.app.SearchManager
import android.content.Context.SEARCH_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.julianawl.anitime.MyApplication
import br.com.julianawl.anitime.R
import br.com.julianawl.anitime.model.AnimeItem
import br.com.julianawl.anitime.ui.adapter.AnimesAdapter
import br.com.julianawl.anitime.ui.details.AnimeDetailsFragment
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.fragment_discover.*


class DiscoverFragment : Fragment() {

    private val viewModel: DiscoverViewModel by viewModels {
        DiscoverViewModelFactory((activity?.application as MyApplication).repository)
    }

    private val adapter by lazy {
        context?.let {
            AnimesAdapter(context = it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getAnimes()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_discover,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val toolbar = view.findViewById<MaterialToolbar>(R.id.topAppBarDisc)
//        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_discover))
//        val navHostFragment = NavHostFragment.findNavController(this)
//        NavigationUI.setupWithNavController(toolbar, navHostFragment, appBarConfiguration)
//        setHasOptionsMenu(true)
//        (activity as AppCompatActivity).setSupportActionBar(toolbar)
//        toolbar.setNavigationOnClickListener { view ->
//            view.findNavController().navigateUp()
//        }
        configuraDiscover()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.top_app_bar_discover, menu)
        val searchManager = activity?.getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = menu.findItem(R.id.search_anime).actionView as SearchView
        val searchMenuItem = menu.findItem(R.id.search_anime)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = "Search an Anime..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.length!! > 3) {
                    viewModel.getSearch(query)
                    viewModel.mSearchResponse.observe(this@DiscoverFragment, {
                        if(it.isSuccessful){
                            it.body()?.let {
                                    result ->

                                adapter?.append(result.results)
                                Log.i("SEARCH", result.results.toString())
                            }
                        }
                    })

                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.getSearch(newText)
                viewModel.mSearchResponse.observe(this@DiscoverFragment, {
                    if(it.isSuccessful){
                        it.body()?.let {
                            result -> adapter?.append(result.results)
                        }
                    }
                })
                return true
            }

        })
        searchMenuItem.icon.setVisible(false, false)
    }

    private fun configuraDiscover() {
        adapter?.onItemClickListener = {
            goToDetails(it)
        }
        discover_list.adapter = adapter
        discover_list.layoutManager = LinearLayoutManager(context)
    }

    private fun getAnimes() {
        viewModel.getAnimes()
        viewModel.mResponse.observe(this, {
            if (it.isSuccessful) {
                it.body()?.let { animes -> adapter?.append(animes.animes) }

            } else {
                Log.i("Response", it.errorBody().toString())
            }
        })
    }

    private fun goToDetails(anime: AnimeItem){
        val details = AnimeDetailsFragment(anime)
        val fragmentManager = activity?.supportFragmentManager
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment_discover_container, details)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

}