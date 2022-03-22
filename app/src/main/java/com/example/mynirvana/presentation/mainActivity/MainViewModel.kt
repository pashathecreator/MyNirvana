package com.example.mynirvana.presentation.mainActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _isLoading = MutableLiveData(true)
    val isLoading = _isLoading

    init {
        viewModelScope.launch {
            delay(1500)
            _isLoading.postValue(false)
        }
    }
}