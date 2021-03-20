package br.com.julianawl.anitime.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.julianawl.anitime.R
import br.com.julianawl.anitime.model.AnimeItem
import br.com.julianawl.anitime.ui.adapter.extensions.ratingBarFormat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import kotlinx.android.synthetic.main.item_list_discover.view.*

//adapter dos recyclerviews
class AnimesAdapter(
    private val context: Context,
    private val animes: MutableList<AnimeItem> = mutableListOf(),
    var onItemClickListener: (anime: AnimeItem) -> Unit = {}
) : RecyclerView.Adapter<AnimesAdapter.AnimeViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnimeViewHolder {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.item_list_discover, parent, false)
        return AnimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bind(animes[position])
    }

    override fun getItemCount(): Int = animes.size

    fun append(animes: List<AnimeItem>) {
        this.animes.clear()
        this.animes.addAll(animes)
        notifyDataSetChanged()
    }


    inner class AnimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var anime: AnimeItem
        private val animeName by lazy {
            itemView.anime_name
        }
        private val animeEpisodes by lazy {
            itemView.anime_episodes
        }
        private val animeStartDate by lazy {
            itemView.anime_start_date
        }
        private val animeEndDate by lazy {
            itemView.anime_end_date
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
                if(::anime.isInitialized){
                    onItemClickListener(anime)
                }
            }
        }

        fun bind(anime: AnimeItem) {
            this.anime = anime
            animeName.text = anime.title
            episodesFormat(anime.episodes)
            animeStartDate.text = anime.startDate
            animeEndDate.text = anime.endDate
            ratingBarFormat(animeScoreStars, anime.score)
            animeScore.text = anime.score.toString()
            Glide.with(itemView)
                .load(anime.imageUrl)
                .transform(CenterCrop())
                .into(animePoster)
        }

        private fun episodesFormat(episodes: Int?) {
            if (episodes == null) {
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