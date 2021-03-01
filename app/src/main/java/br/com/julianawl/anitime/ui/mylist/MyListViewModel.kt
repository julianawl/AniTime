package br.com.julianawl.anitime.ui.mylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is my list Fragment"
    }
    val text: LiveData<String> = _text
}