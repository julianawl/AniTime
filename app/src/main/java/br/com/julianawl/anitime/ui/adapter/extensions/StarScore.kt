package br.com.julianawl.anitime.ui.adapter.extensions

import androidx.appcompat.widget.AppCompatRatingBar

fun ratingBarFormat(anime: AppCompatRatingBar, score: Float) {
    anime.numStars = 5
    anime.max = 5
    anime.stepSize = 0.01F
    anime.rating = ((score * 5) / 10)
}