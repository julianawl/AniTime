package br.com.julianawl.anitime.ui.mylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.julianawl.anitime.repository.AnimesRepository

class MyListViewModelFactory(private val repository: AnimesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MyListViewModel(repository) as T
    }

}
