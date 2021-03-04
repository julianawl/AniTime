package br.com.julianawl.anitime.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.julianawl.anitime.repository.AnimesRepository

class AnimeDetailsViewModelFactory(
    private val repository: AnimesRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AnimeDetailsViewModel(repository) as T
    }
}