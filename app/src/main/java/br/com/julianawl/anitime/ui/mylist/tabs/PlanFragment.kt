package br.com.julianawl.anitime.ui.mylist.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.julianawl.anitime.MyApplication
import br.com.julianawl.anitime.R
import br.com.julianawl.anitime.ui.adapter.AnimesAdapter
import br.com.julianawl.anitime.ui.mylist.MyListViewModel
import br.com.julianawl.anitime.ui.mylist.MyListViewModelFactory
import kotlinx.android.synthetic.main.fragment_plan.*

//item do tablayout
class PlanFragment : Fragment() {

    private val viewModel: MyListViewModel by viewModels {
        MyListViewModelFactory((activity?.application as MyApplication).repository)
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
            R.layout.fragment_plan,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuraPlan()
    }

    //configura a lista com os animes que planeja assistir
    private fun configuraPlan() {
        list_plan.adapter = adapter
        list_plan.layoutManager = LinearLayoutManager(context)
    }

    //pega os items da lista salvos internamente
    private fun getAnimes() {
        viewModel.allPTW.observe(this, {
            it?.let {
                adapter?.append(it)
            }
        })
    }
}