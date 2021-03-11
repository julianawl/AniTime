package br.com.julianawl.anitime.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.julianawl.anitime.repository.AnimesRepository

class DiscoverViewModelFactory(
    private val repository: AnimesRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DiscoverViewModel(repository) as T
    }
}