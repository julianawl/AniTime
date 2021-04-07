package br.com.julianawl.anitime.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.julianawl.anitime.R
import br.com.julianawl.anitime.model.AnimeItem
import br.com.julianawl.anitime.ui.adapter.AnimesLocaleAdapter.AnimesLocaleViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import kotlinx.android.synthetic.main.item_list_local.view.*

class AnimesLocaleAdapter(
    private val context: Context,
    private val animes: MutableList<AnimeItem> = mutableListOf(),
    var onClickDelete: (anime: AnimeItem) -> Unit = {},
    var onClickEdit: (anime: AnimeItem) -> Unit = {}
) : RecyclerView.Adapter<AnimesLocaleViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnimesLocaleViewHolder {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.item_list_local, parent, false)
        return AnimesLocaleViewHolder(view)
    }

    override fun getItemCount(): Int = animes.size

    override fun onBindViewHolder(holder: AnimesLocaleViewHolder, position: Int) {
        holder.bind(animes[position])
    }

    fun append(animes: List<AnimeItem>) {
        this.animes.clear()
        this.animes.addAll(animes)
        notifyDataSetChanged()
    }

    fun deleteAnime(anime: AnimeItem) {
        this.animes.remove(anime)
        notifyItemRemoved(animes.indexOf(anime))
    }

    inner class AnimesLocaleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var anime: AnimeItem
        private val animeName by lazy {
            itemView.anime_name
        }
        private val animePoster by lazy {
            itemView.anime_poster
        }
        private val animeDelete by lazy {
            itemView.anime_delete
        }
        private val animeEdit by lazy {
            itemView.anime_edit
        }

        init {
            animeDelete?.let {
                it.setOnClickListener {
                    if (::anime.isInitialized) {
                        onClickDelete(anime)

                    }
                }
            }
        }

        init {
            animeEdit?.let {
                it.setOnClickListener {
                    if (::anime.isInitialized) {
                        onClickEdit(anime)
                    }
                }
            }
        }

        fun bind(anime: AnimeItem) {
            this.anime = anime
            animeName.text = anime.title
            Glide.with(itemView)
                .load(anime.imageUrl)
                .transform(CenterCrop())
                .into(animePoster)
        }

    }

}
