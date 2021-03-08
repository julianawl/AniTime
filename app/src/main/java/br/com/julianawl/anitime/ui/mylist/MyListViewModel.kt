package br.com.julianawl.anitime.ui.mylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import br.com.julianawl.anitime.model.AnimeItem
import br.com.julianawl.anitime.repository.AnimesRepository

class MyListViewModel(repository: AnimesRepository ) : ViewModel() {

    val allComplete: LiveData<List<AnimeItem>> = repository.allCompleteAnimes.asLiveData()

    val allPTW: LiveData<List<AnimeItem>> = repository.allPTWAnimes.asLiveData()
}