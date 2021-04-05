package br.com.julianawl.anitime.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import br.com.julianawl.anitime.MyApplication
import br.com.julianawl.anitime.R
import br.com.julianawl.anitime.model.AnimeDetails
import br.com.julianawl.anitime.ui.*
import br.com.julianawl.anitime.ui.adapter.extensions.ratingBarFormat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_anime_details.*
import retrofit2.Response

class AnimeDetailsFragment : Fragment() {

    var index = INITIAL_INDEX
    private val lists = arrayOf(COMPLETE, PLAN_TO_WATCH)

    private val argument by navArgs<AnimeDetailsFragmentArgs>()

    private val anime by lazy {
        argument.anime
    }

    private val viewModel: AnimeDetailsViewModel by viewModels {
        AnimeDetailsViewModelFactory(
            (activity?.application
                    as MyApplication).repository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_anime_details,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuraAppBar(view)
        configuraDetails()
    }

    //configura as informações que aparecerão na tela
    private fun configuraDetails() {
        viewModel.getDetails(anime.id)
        viewModel.mResponse.observe(viewLifecycleOwner, {
            if (it.isSuccessful) {
                anime_details_type.text = it.body()?.type
                configuraEpisodes(it)
                configuraStudio(it)
                anime_details_date.text = it.body()?.date?.completeDate
                anime_details_synopsis.text = it.body()?.synopsis
                configuraScore(it)
                configuraPoster(it)
            }
        })
    }

    private fun configuraEpisodes(animeDetails: Response<AnimeDetails>) {
        anime_details_episodes.text = animeDetails.body()?.let { response ->
            response.episodes.let {
                it?.toString() ?: "?"
            }
        }
    }

    private fun configuraStudio(animeDetails: Response<AnimeDetails>) {
        anime_details_studio.text = animeDetails.body()?.let { response ->
            response.studio.let { studioList ->
                if (studioList.isEmpty()) {
                    EMPTY_ITEM
                } else {
                    studioList[0].studioName
                }
            }
        }
    }

    private fun configuraScore(animeDetails: Response<AnimeDetails>) {
        val score = animeDetails.body()!!.score
        ratingBarFormat(anime_details_star_score, score)
    }

    private fun configuraPoster(animeDetails: Response<AnimeDetails>) {
        Glide.with(requireContext())
            .load(animeDetails.body()?.imageUrl)
            .transform(CenterCrop())
            .into(anime_details_poster)
    }

    //configura as ações presentes na toolbar
    private fun configuraAppBar(view: View) {
        val toolbar = view.findViewById<MaterialToolbar>(R.id.topAppBar)
        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(toolbar, navHostFragment)
        topAppBar.title = anime.title
        topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.add_anime -> {
                    configuraDialogAddAnime()
                    true
                }
                else -> false
            }
        }
    }

    //configura dialog de adição do anime em uma lista
    private fun configuraDialogAddAnime() {
        var selectItem = lists[index]

        MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertDialog_Theme)
            .setTitle(R.string.add_anime)
            .setSingleChoiceItems(lists, index) { _, which ->
                index = which
                selectItem = lists[which]
            }
            .setPositiveButton(SAVE) { dialog, _ ->
                if (selectItem == lists[0]) {
                    addAnimeComplete()
                    Toast.makeText(
                        requireContext(),
                        ADD_ANIME_MESSAGE_COMPLETE,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    addAnimePTW()
                    Toast.makeText(
                        requireContext(),
                        ADD_ANIME_MESSAGE_PTW,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                dialog.dismiss()
            }
            .setNeutralButton(CANCEL) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun addAnimePTW() {
        //verificar se anime já está na lista ou se está na outra lista
        viewModel.savePTWList(anime)
    }

    private fun addAnimeComplete() {
        //verificar se anime já está na lista ou se está na outra lista
        viewModel.saveCompleteList(anime)
    }
}