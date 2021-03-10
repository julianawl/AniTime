package br.com.julianawl.anitime.ui.discover

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.julianawl.anitime.model.AnimeItem
import br.com.julianawl.anitime.repository.AnimesRepository
import br.com.julianawl.anitime.retrofit.GetAnimesResponse
import br.com.julianawl.anitime.retrofit.GetSearchResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class DiscoverViewModel(
    private val repository: AnimesRepository
) : ViewModel() {

    val mResponse: MutableLiveData<Response<GetAnimesResponse>> = MutableLiveData()
    val mSearchResponse: MutableLiveData<Response<GetSearchResponse>> = MutableLiveData()

    fun getAnimes(){
        viewModelScope.launch {
            val response = repository.getTopAnimesAiring()
            mResponse.value = response
        }
    }

    fun getSearch(keyword: String){
        viewModelScope.launch {
            val response = repository.getAnimesSearch(keyword)
            mSearchResponse.value = response
        }
    }

}