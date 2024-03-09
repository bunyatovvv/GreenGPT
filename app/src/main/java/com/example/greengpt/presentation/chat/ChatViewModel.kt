package com.example.greengpt.presentation.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greengpt.data.dto.local.FavoriteDTO
import com.example.greengpt.data.dto.remote.dto.ChatDTO
import com.example.greengpt.data.repository.ApiRepoImpl
import com.example.greengpt.data.repository.RoomRepoImpl
import com.example.greengpt.domain.model.remote.ChatPostModel
import com.example.greengpt.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val apiRepo : ApiRepoImpl,
    private val roomRepo : RoomRepoImpl
) : ViewModel() {

    private val _chatResult = MutableLiveData<Resource<ChatDTO>>()
    val chatResult : LiveData<Resource<ChatDTO>> = _chatResult

    fun chat(chatPostModel: ChatPostModel) = viewModelScope.launch(Dispatchers.IO) {
        _chatResult.postValue(Resource.loading(null))
        val response = apiRepo.chat(chatPostModel)
        response.collectLatest {
            _chatResult.postValue(it)
        }
    }

    fun insertFavorite(favoriteDTO: FavoriteDTO) = viewModelScope.launch(Dispatchers.IO) {
       roomRepo.insertFavorite(favoriteDTO)
    }
}