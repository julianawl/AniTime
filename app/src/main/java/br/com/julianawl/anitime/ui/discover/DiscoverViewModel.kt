package br.com.julianawl.anitime.ui.discover

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.julianawl.anitime.repository.AnimesRepository
import br.com.julianawl.anitime.retrofit.response.GetAnimesResponse
import br.com.julianawl.anitime.retrofit.response.GetSearchResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class DiscoverViewModel(
    private val repository: AnimesRepository
) : ViewModel() {

    val mResponse: MutableLiveData<Response<GetAnimesResponse>> = MutableLiveData()
    val mSearchResponse: MutableLiveData<Response<GetSearchResponse>> = MutableLiveData()

    fun getAnimes(page: Int) {
        viewModelScope.launch {
            val response = repository.getTopAnimesAiring(page)
            mResponse.value = response
        }
    }

    fun getSearch(keyword: String) {
        viewModelScope.launch {
            val response = repository.getAnimesSearch(keyword)
            mSearchResponse.value = response
        }
    }

}