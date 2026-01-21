package com.farazsheikh.fusedlocation.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.farazsheikh.fusedlocation.domain.repository.LocationLogApiRepository
import com.farazsheikh.fusedlocation.domain.repository.LogLocationStoreRepository

class MainViewModelFactory(

) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel() as T
    }
}