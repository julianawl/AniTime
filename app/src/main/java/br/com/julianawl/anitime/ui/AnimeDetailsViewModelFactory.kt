package br.com.julianawl.anitime.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.julianawl.anitime.repository.AnimesRepository
import br.com.julianawl.anitime.ui.discover.DiscoverViewModel

class AnimeDetailsViewModelFactory(
    private val repository: AnimesRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AnimeDetailsViewModel(repository) as T
    }
}