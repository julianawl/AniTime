package br.com.julianawl.anitime.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import br.com.julianawl.anitime.R
import br.com.julianawl.anitime.model.AnimeDisc
import br.com.julianawl.anitime.repository.AnimesRepository
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import kotlinx.android.synthetic.main.fragment_anime_details.*

class AnimeDetailsFragment(
    private val anime: AnimeDisc
) : Fragment() {

    lateinit var viewModel: AnimeDetailsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = AnimesRepository()
        val viewModelFactory = AnimeDetailsViewModelFactory(repository)

        viewModel = ViewModelProvider(
            this,
            viewModelFactory)
            .get(AnimeDetailsViewModel::class.java)

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

        configuraDetails()
    }


    private fun configuraDetails() {
        viewModel.getDetails(anime.id)
        viewModel.mResponse.observe(viewLifecycleOwner,{
            if(it.isSuccessful){
                anime_details_english_title.text = it.body()?.titleEnglish
                anime_details_episodes.text = it.body()?.episodes.toString()
                anime_details_date.text = it.body()?.date?.completeDate
                anime_details_synopsis.text = it.body()?.synopsis
                Glide.with(requireContext())
                    .load(it.body()?.imageUrl)
                    .transform(CenterCrop())
                    .into(anime_details_poster)
            }
        })
    }


}