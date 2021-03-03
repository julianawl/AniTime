package br.com.julianawl.anitime.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.julianawl.anitime.R
import br.com.julianawl.anitime.model.AnimeDisc
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import kotlinx.android.synthetic.main.item_list.view.*

class AnimesAdapter(
    private val context: Context,
    private val animes: MutableList<AnimeDisc> = mutableListOf(),
    var onItemClickListener: (anime: AnimeDisc) -> Unit = {}
) : RecyclerView.Adapter<AnimesAdapter.AnimeViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnimeViewHolder {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.item_list, parent, false)
        return AnimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bind(animes[position])
    }

    override fun getItemCount(): Int = animes.size

    fun append(animes: List<AnimeDisc>) {
        this.animes.addAll(animes)
        notifyDataSetChanged()
    }

    inner class AnimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var anime: AnimeDisc
        private val animeName by lazy {
            itemView.anime_name
        }
        private val animeEpisodes by lazy {
            itemView.anime_episodes
        }
        private val animeDate by lazy {
            itemView.anime_start_date
        }
        private val animePoster by lazy {
            itemView.anime_poster
        }
        private val animeScoreStars by lazy {
            itemView.anime_score_stars
        }
        private val animeScore by lazy {
            itemView.anime_score
        }

        init {
            itemView.setOnClickListener {
                if (::anime.isInitialized) {
                    onItemClickListener(anime)
                }
            }
        }

        fun bind(anime: AnimeDisc) {
            this.anime = anime
            animeName.text = anime.title
            episodesFormat(anime.episodes)
            animeDate.text = anime.startDate
            ratingBarFormat(anime.score)
            animeScore.text = anime.score.toString()
            Glide.with(itemView)
                .load(anime.imageUrl)
                .transform(CenterCrop())
                .into(animePoster)
        }

        private fun ratingBarFormat(score: Float) {
            animeScoreStars.numStars = 5
            animeScoreStars.max = 5
            animeScoreStars.stepSize = 0.01F
            animeScoreStars.rating = ((score*5)/10)
            Log.i("RATING BAR: ", (animeScoreStars.rating.toString()))
        }

        private fun episodesFormat(episodes: Int) {
            if (episodes == 0) {
                ("? episodes")
                    .also {
                        animeEpisodes.text = it
                    }
            } else {
                if (episodes == 1) {
                    ("$episodes episode")
                        .also {
                            animeEpisodes.text = it
                        }
                } else {
                    ("$episodes episodes")
                        .also {
                            animeEpisodes.text = it
                        }
                }
            }
        }


    }

}