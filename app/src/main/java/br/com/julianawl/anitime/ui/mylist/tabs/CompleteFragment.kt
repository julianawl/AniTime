package br.com.julianawl.anitime.ui.mylist.tabs

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.julianawl.anitime.MyApplication
import br.com.julianawl.anitime.R
import br.com.julianawl.anitime.model.AnimeItem
import br.com.julianawl.anitime.ui.adapter.AnimesLocaleAdapter
import br.com.julianawl.anitime.ui.mylist.MyListViewModel
import br.com.julianawl.anitime.ui.mylist.MyListViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_complete.*

//item do tablayout
class CompleteFragment : Fragment() {

    private val viewModel: MyListViewModel by viewModels {
        MyListViewModelFactory((activity?.application as MyApplication).repository)
    }

    private val adapter by lazy {
        context?.let {
            AnimesLocaleAdapter(context = it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getCompleteList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_complete,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuraComplete()
    }

    //configura a lista dos animes completos
    private fun configuraComplete() {
        list_complete.adapter = adapter
        list_complete.layoutManager = LinearLayoutManager(context)
        adapter?.onClickListener = {
            deleteAnimesComplete(it)
        }
    }

    //pega os animes da lista salvos localmente
    private fun getCompleteList() {
        viewModel.allComplete.observe(this, {
            it?.let {
                it.sortedBy { it.title }
                adapter?.append(it)
            }
        })
    }

    private fun deleteAnimesComplete(anime: AnimeItem){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Remove warning")
            .setMessage("Do you really want to remove '${anime.title}' from your list?")
            .setPositiveButton("YES") { dialog, _ ->
                viewModel.deleteAnimeComplete(anime)
                adapter?.deleteAnime(anime)
                dialog.dismiss()
                Toast.makeText(requireContext(),
                    "'${anime.title}' removed",
                    Toast.LENGTH_SHORT).show()
            }
            .setNeutralButton("NO") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }
}