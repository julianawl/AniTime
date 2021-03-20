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
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.julianawl.anitime.MyApplication
import br.com.julianawl.anitime.R
import br.com.julianawl.anitime.model.AnimeItem
import br.com.julianawl.anitime.ui.LoadingDialog
import br.com.julianawl.anitime.ui.adapter.AnimesAdapter
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.fragment_discover.*

class DiscoverFragment : Fragment() {

    private val loadingDialog = LoadingDialog()

    private val viewModel: DiscoverViewModel by viewModels {
        DiscoverViewModelFactory((activity?.application as MyApplication).repository)
    }

    private val adapter by lazy {
        context?.let {
            AnimesAdapter(context = it)
        }
    }

    private val controller by lazy {
        findNavController()
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

        configuraAppBar(view)
        configuraDiscover()
    }

    //configura a toolbar com as ações
    private fun configuraAppBar(view: View) {
        val toolbar = view.findViewById<MaterialToolbar>(R.id.topAppBarDisc)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_discover))
        val navHostFragment = NavHostFragment.findNavController(this)

        NavigationUI.setupWithNavController(toolbar, navHostFragment, appBarConfiguration)
        setHasOptionsMenu(true)

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }
    }

    //configura a função de pesquisa na appbar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.top_app_bar_discover, menu)

        val searchManager = activity?.getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = menu.findItem(R.id.search_anime).actionView as SearchView
        val searchMenuItem = menu.findItem(R.id.search_anime)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = "Search an anime..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                configuraSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                configuraSearch(newText)
                return true
            }
        })
        searchMenuItem.icon.setVisible(false, false)
    }

    private fun configuraSearch(query: String?) {
        if (query?.length!! > 3) {
            viewModel.getSearch(query)
            viewModel.mSearchResponse.observe(this@DiscoverFragment, {
                if (it.isSuccessful) {
                    it.body()?.let { result ->
                        adapter?.append(result.results)
                    }
                }
            })
        }
    }

    //define o adapter e a ação quando clica no anime
    private fun configuraDiscover() {
        adapter?.onItemClickListener = {
            goToDetails(it)
        }
        discover_list.adapter = adapter
        discover_list.layoutManager = LinearLayoutManager(context)
    }

    //chama os animes da lista discover
    private fun getAnimes() {
        loadingDialog.show(requireContext())
        viewModel.getAnimes()
        viewModel.mResponse.observe(this, {
            if (it.isSuccessful) {
                it.body()?.let { animes ->
                    adapter?.append(animes.animes)
                    loadingDialog.dismissDialog()
                }

            } else {
                //definir ação para erro
                Log.i("Response", it.errorBody().toString())
            }
        })
    }

    //vai para os detalhes quando clica em um anime
    private fun goToDetails(anime: AnimeItem) {
        val direction = DiscoverFragmentDirections
            .actionNavigationDiscoverToDetails(anime)
        controller.navigate(direction)
    }

}