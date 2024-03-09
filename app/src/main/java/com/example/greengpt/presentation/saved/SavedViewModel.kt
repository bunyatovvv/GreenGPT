package com.example.greengpt.presentation.saved

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greengpt.data.dto.local.FavoriteDTO
import com.example.greengpt.data.repository.RoomRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val roomRepo : RoomRepoImpl
) : ViewModel() {

    private val _savedResult = MutableLiveData<List<FavoriteDTO>>()
    val savedResult : LiveData<List<FavoriteDTO>> = _savedResult

    fun getFavorites() {
        viewModelScope.launch {
            roomRepo.getAllFavorites()
                .flowOn(Dispatchers.IO)
                .catch { e->
                    Log.d("error", e.message.toString())
                }
                .collect {
                    _savedResult.postValue(it)
                }
        }
    }

    fun deleteFavorite(favoriteDTO: FavoriteDTO) = viewModelScope.launch(Dispatchers.IO) {
        roomRepo.deleteFavorite(favoriteDTO)
    }
}