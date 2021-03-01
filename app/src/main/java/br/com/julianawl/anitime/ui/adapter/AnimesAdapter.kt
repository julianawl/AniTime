package br.com.julianawl.anitime.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.julianawl.anitime.R
import br.com.julianawl.anitime.model.Anime
import br.com.julianawl.anitime.retrofit.GetAnimesResponse
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import kotlinx.android.synthetic.main.item_list.view.*

class AnimesAdapter(
    private val context: Context,
    private val animes: MutableList<Anime> = mutableListOf(),
    var onItemClickListener: (anime: Anime) -> Unit = {}
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

    fun append(animes: List<Anime>){
        this.animes.addAll(animes)
        notifyDataSetChanged()
    }

    inner class AnimeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var anime: Anime
        private val animeName by lazy {
            itemView.anime_name
        }
        private val animeEpisodes by lazy {
            itemView.anime_episodes
        }
        private val animePoster by lazy {
            itemView.anime_poster
        }

        init{
            itemView.setOnClickListener {
                if(::anime.isInitialized){
                    onItemClickListener(anime)
                }
            }
        }

        fun bind(anime: Anime) {
            this.anime = anime
            animeName.text = anime.title
            animeEpisodes.text = anime.episodes.toString()
            Glide.with(itemView)
                .load(anime.imageUrl)
                .transform(CenterCrop())
                .into(animePoster)
        }


    }

}