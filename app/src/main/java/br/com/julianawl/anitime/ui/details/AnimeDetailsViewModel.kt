package br.com.julianawl.anitime.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.julianawl.anitime.model.AnimeDetails
import br.com.julianawl.anitime.model.AnimeItem
import br.com.julianawl.anitime.repository.AnimesRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class AnimeDetailsViewModel(
    private val repository: AnimesRepository
) : ViewModel() {

    val mResponse: MutableLiveData<Response<AnimeDetails>> = MutableLiveData()

    fun getDetails(id: Long) {
        viewModelScope.launch {
            val response = repository.getAnimeDetails(id)
            mResponse.value = response
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