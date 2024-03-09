package com.example.greengpt.presentation.chat

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greengpt.data.dto.local.FavoriteDTO
import com.example.greengpt.data.mapper.toMessage
import com.example.greengpt.databinding.FragmentChatBinding
import com.example.greengpt.domain.model.local.MessageModel
import com.example.greengpt.domain.model.remote.ChatPostModel
import com.example.greengpt.domain.model.remote.Message
import com.example.greengpt.presentation.chat.adapter.ChatAdapter
import com.example.greengpt.presentation.saved.adapter.SavedAdapter
import com.example.greengpt.util.Constants.FIRST_ID
import com.example.greengpt.util.Constants.LOADING_ID
import com.example.greengpt.util.Constants.RECEIVE_ID
import com.example.greengpt.util.Constants.SEND_ID
import com.example.greengpt.util.Status
import com.example.greengpt.util.Time
import com.example.greengpt.util.gone
import com.example.greengpt.util.shareTextPlain
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ChatViewModel>()
    private val chatAdapter by lazy { ChatAdapter() }
    private val savedAdapter by lazy { SavedAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val time = Time.timeStamp()
        val messageModel = MessageModel("Hi, how can i help you?", FIRST_ID, time)
        chatAdapter.insertMessage(messageModel)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)

        binding.chatRv.adapter = chatAdapter
        binding.chatRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        setSuggest()

        if (chatAdapter.messageList.size >1){
            binding.suggestLayout.gone()
        }

        binding.sendCv.setOnClickListener {
            if (binding.inutEditText.text.toString().isNotEmpty()) {
                binding.suggestLayout.gone()
                val content = binding.inutEditText.text.toString()
                val message = Message(content = content)
                val messageList = mutableListOf<Message>()
                messageList.add(message)

                val postModel = ChatPostModel(
                    messages = messageList
                )
                viewModel.chat(postModel)
                val time = Time.timeStamp()
                chatAdapter.insertMessage(MessageModel(content, SEND_ID, time))
                chatAdapter.insertMessage(MessageModel("Thinking...", LOADING_ID, "14:43"))
                binding.chatRv.scrollToPosition(chatAdapter.itemCount - 1)
            }
        }




        chatAdapter.setOnSaveClickedListener {
            val time = Time.timeStamp()
            val favorite = FavoriteDTO(
                chatAdapter.messageList[chatAdapter.messageList.size - 2].message,
                it.message,
                time,
                0
            )
            viewModel.insertFavorite(favorite)
            savedAdapter.notifyDataSetChanged()
        }
        chatAdapter.setOnShareClickedListener {
            val intent = Intent()
            intent.shareTextPlain(it.message)
            startActivity(Intent.createChooser(intent, "Share on social medias"))
        }
        observeLiveData()
        return binding.root
    }


    @SuppressLint("NotifyDataSetChanged")
    fun observeLiveData() {
        viewModel.chatResult.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    val mes = it.data!!.toMessage()
                    val message = MessageModel(mes, RECEIVE_ID,"16")

                    chatAdapter.messageList.last().message = mes
                    chatAdapter.messageList.last().id = RECEIVE_ID


                    binding.sendCv.isClickable = true
                    binding.inutEditText.isActivated = true
                    binding.inutEditText.isClickable = true
                    chatAdapter.notifyDataSetChanged()
                }

                Status.LOADING -> {


                    binding.sendCv.isClickable = false
                    binding.inutEditText.isActivated = false
                    binding.inutEditText.isClickable = false
                    Log.e("deeebbb",chatAdapter.messageList.toString())
                }

                Status.ERROR -> {
                    customMessage(it.message ?: "error")
                }
            }
        })
    }

    private fun customMessage(message: String) {
        val time = Time.timeStamp()
        chatAdapter.insertMessage(MessageModel(message, LOADING_ID, time))
        binding.chatRv.scrollToPosition(chatAdapter.itemCount - 1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun writeText(text: String, editText: EditText) {
        editText.setText(text)
    }

    private fun setSuggest() {
        val firstSuggest = binding.firstSuggestText
        val secondSuggest = binding.secondSuggestText
        val thirdSuggest = binding.thirdSuggestText

        firstSuggest.setOnClickListener {
            writeText(firstSuggest.text.toString(), binding.inutEditText)
            binding.suggestLayout.gone()
        }

        secondSuggest.setOnClickListener {
            writeText(secondSuggest.text.toString(), binding.inutEditText)
            binding.suggestLayout.gone()
        }

        thirdSuggest.setOnClickListener {
            writeText(thirdSuggest.text.toString(), binding.inutEditText)
            binding.suggestLayout.gone()
        }
    }
}