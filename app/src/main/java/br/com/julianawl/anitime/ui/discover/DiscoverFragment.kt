package br.com.julianawl.anitime.ui.discover

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.julianawl.anitime.R
import br.com.julianawl.anitime.repository.AnimesRepository
import br.com.julianawl.anitime.ui.adapter.AnimesAdapter
import kotlinx.android.synthetic.main.fragment_discover.*


class DiscoverFragment : Fragment() {

    private lateinit var discoverViewModel: DiscoverViewModel

    private val adapter by lazy {
        context?.let {
            AnimesAdapter(context = it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = AnimesRepository()
        val viewModelFactory = DiscoverViewModelFactory(repository)

        discoverViewModel = ViewModelProvider(
            this,
            viewModelFactory)
            .get(DiscoverViewModel::class.java)

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
//        adapter?.onItemClickListener = {
//            goToDetails(it.id)
//        }
        discover_list.adapter = adapter
        discover_list.layoutManager = LinearLayoutManager(context)
    }

    private fun getAnimes() {
        discoverViewModel.getAnimes()
        discoverViewModel.mResponse.observe(this, {
            if (it.isSuccessful) {
                it.body()?.let { animes -> adapter?.append(animes.animes)
                    Log.i("getAnimes: ", animes.toString())}

            } else {
                Log.i("Response", it.errorBody().toString())
            }
        })
    }

    private fun goToDetails(id: Long) {
        TODO()
    }
}