package br.com.julianawl.anitime.ui.discover

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.julianawl.anitime.MyApplication
import br.com.julianawl.anitime.R
import br.com.julianawl.anitime.model.AnimeItem
import br.com.julianawl.anitime.ui.adapter.AnimesAdapter
import br.com.julianawl.anitime.ui.details.AnimeDetailsFragment
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
        configuraDiscover()

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
                it.body()?.let { animes -> adapter?.append(animes.animes)
                    Log.i("getAnimes: ", animes.toString())}

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