package com.example.androidboilerplate.screens.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidboilerplate.data.network.baseModel.BaseApiResponseModel
import com.example.androidboilerplate.repository.network.BaseApiRepository
import com.example.androidboilerplate.screens.home.model.network.MusicAlbums
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val baseApiRepository: BaseApiRepository
) : ViewModel() {

    var musicAlbums: MutableLiveData<BaseApiResponseModel<MusicAlbums>> = MutableLiveData()

    fun getHomeData() {
        viewModelScope.launch {
            musicAlbums.value = BaseApiResponseModel.Loading()
            musicAlbums.value = baseApiRepository.getMoviesAlbums()
        }
    }
}