package br.com.julianawl.anitime.ui.mylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import br.com.julianawl.anitime.model.AnimeItem
import br.com.julianawl.anitime.repository.AnimesRepository
import kotlinx.coroutines.launch

class MyListViewModel(private val repository: AnimesRepository) : ViewModel() {

    val allComplete: LiveData<List<AnimeItem>> = repository.allCompleteAnimes.asLiveData()
    val allPTW: LiveData<List<AnimeItem>> = repository.allPTWAnimes.asLiveData()

    fun deleteAnimeComplete(anime: AnimeItem) {
        viewModelScope.launch {
            repository.deleteCompleteList(anime)
        }
    }

    fun deleteAnimePlan(anime: AnimeItem) {
        viewModelScope.launch {
            repository.deletePTWList(anime)
        }
    }

    fun saveCompleteList(anime: AnimeItem) {
        viewModelScope.launch {
            repository.saveCompleteList(anime)
        }
    }

    fun savePTWList(anime: AnimeItem) {
        viewModelScope.launch {
            repository.savePTWList(anime)
        }
    }
}