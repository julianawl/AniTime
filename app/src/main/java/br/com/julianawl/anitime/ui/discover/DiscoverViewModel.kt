package br.com.julianawl.anitime.ui.discover

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.julianawl.anitime.model.AnimeItem
import br.com.julianawl.anitime.repository.AnimesRepository
import br.com.julianawl.anitime.retrofit.GetAnimesResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class DiscoverViewModel(
    private val repository: AnimesRepository
) : ViewModel() {

    val mResponse: MutableLiveData<Response<GetAnimesResponse>> = MutableLiveData()

    fun getAnimes(){
        viewModelScope.launch {
            val response = repository.getTopAnimesAiring()
            mResponse.value = response
        }
    }

}