package com.example.greengpt.presentation.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greengpt.data.dto.remote.dto.ChatDTO
import com.example.greengpt.data.repository.ApiRepoImpl
import com.example.greengpt.domain.remote.model.ChatPostModel
import com.example.greengpt.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repo : ApiRepoImpl
) : ViewModel() {

    private val _chatResult = MutableLiveData<Resource<ChatDTO>>()
    val chatResult : LiveData<Resource<ChatDTO>> = _chatResult

    fun chat(chatPostModel: ChatPostModel) = viewModelScope.launch {
        _chatResult.postValue(Resource.loading(null))

        val response = repo.chat(chatPostModel)
        response.collectLatest {
            _chatResult.postValue(it)
        }
    }
}