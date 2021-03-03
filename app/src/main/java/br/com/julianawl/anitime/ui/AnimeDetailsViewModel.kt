package br.com.julianawl.anitime.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.julianawl.anitime.repository.AnimesRepository
import br.com.julianawl.anitime.retrofit.GetDetailsResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class AnimeDetailsViewModel(
    private val repository: AnimesRepository
): ViewModel() {

    val mResponse: MutableLiveData<Response<GetDetailsResponse>> = MutableLiveData()

    fun getDetails(id: Long){
        viewModelScope.launch {
            val response = repository.getAnimeDetails(id)
            mResponse.value = response
        }
    }
}