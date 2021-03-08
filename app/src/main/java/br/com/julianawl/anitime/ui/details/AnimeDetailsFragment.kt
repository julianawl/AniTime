package br.com.julianawl.anitime.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.julianawl.anitime.MyApplication
import br.com.julianawl.anitime.R
import br.com.julianawl.anitime.model.AnimeDetails
import br.com.julianawl.anitime.model.AnimeItem
import br.com.julianawl.anitime.ui.ratingBarFormat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_anime_details.*
import retrofit2.Response

class AnimeDetailsFragment(
    private val anime: AnimeItem
) : Fragment() {

    private val viewModel: AnimeDetailsViewModel by viewModels {
        AnimeDetailsViewModelFactory((activity?.application as MyApplication).repository)
    }

    var index = 0
    private val lists = arrayOf("Complete", "Plan to watch")

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

        configuraDetails()
        navigationAppBar()
    }


    private fun configuraDetails() {
        viewModel.getDetails(anime.id)
        viewModel.mResponse.observe(viewLifecycleOwner, {
            if (it.isSuccessful) {
                topAppBar.title = it.body()?.title
                anime_details_type.text = it.body()?.type
                anime_details_episodes.text = it.body()?.episodes.toString()
                configuraStudio(it)
                anime_details_date.text = it.body()?.date?.completeDate
                anime_details_synopsis.text = it.body()?.synopsis
                configuraScore(it)
                configuraPoster(it)
            }

        })
    }

    private fun configuraStudio(animeDetails: Response<AnimeDetails>){
        anime_details_studio.text = animeDetails.body()?.let { response ->
            response.studio.let { studioList ->
                if (studioList.isEmpty()) {
                    "-"
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

    private fun navigationAppBar(){
        topAppBar.setNavigationOnClickListener {

        }
        topAppBar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.add_anime -> {
                    var selectItem = lists[index]
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle(R.string.add_anime)
                        .setSingleChoiceItems(lists, index) { _, which ->
                            index = which
                            selectItem = lists[which]
                        }
                        .setPositiveButton("SAVE") { dialog, which ->
                            if (selectItem == lists[0]) {
                                addAnimeComplete()
                            }
                            dialog.dismiss()
                        }
                        .setNeutralButton("CANCEL") { dialog, which ->
                            dialog.dismiss()
                        }.show()


                    true
                }
                else -> false
            }
        }
    }

    private fun addAnimePTW() {
        viewModel.savePTWList(anime)
    }

    private fun addAnimeComplete() {
        viewModel.saveCompleteList(anime)
    }



}