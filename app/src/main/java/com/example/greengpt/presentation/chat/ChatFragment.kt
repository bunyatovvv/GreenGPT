package com.example.greengpt.presentation.chat

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greengpt.data.mapper.toMessage
import com.example.greengpt.databinding.FragmentChatBinding
import com.example.greengpt.domain.local.MessageModel
import com.example.greengpt.domain.remote.model.ChatPostModel
import com.example.greengpt.domain.remote.model.Message
import com.example.greengpt.presentation.chat.adapter.ChatAdapter
import com.example.greengpt.util.Constants.FIRST_ID
import com.example.greengpt.util.Constants.LOADING_ID
import com.example.greengpt.util.Constants.RECEIVE_ID
import com.example.greengpt.util.Constants.SEND_ID
import com.example.greengpt.util.Status
import com.example.greengpt.util.Time
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ChatViewModel>()
    private val chatAdapter by lazy { ChatAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val time = Time.timeStamp()
        val messageModel = MessageModel("Hi, how can i help you?", FIRST_ID,time)
        chatAdapter.insertMessage(messageModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)

        binding.sendCv.setOnClickListener {
            if (binding.inutEditText.text.toString().isNotEmpty()){
                binding.suggestLayout.visibility = View.GONE
                val content = binding.inutEditText.text.toString()
                val message = Message(content = content)
                val messageList = arrayListOf<Message>()
                messageList.add(message)

                val postModel = ChatPostModel(
                    messages = messageList.toList()
                )
                viewModel.chat(postModel)
                val time = Time.timeStamp()
                chatAdapter.insertMessage(MessageModel(content, SEND_ID,time))
                binding.chatRv.scrollToPosition(chatAdapter.itemCount - 1)
            }
        }

        binding.chatRv.adapter = chatAdapter
        binding.chatRv.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        observeLiveData()


        return binding.root
    }


    @SuppressLint("NotifyDataSetChanged")
    fun observeLiveData() {
        viewModel.chatResult.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    val mes = it.data!!.toMessage()
                    chatAdapter.messageList.last().id = RECEIVE_ID
                    chatAdapter.messageList.last().message = mes
                    chatAdapter.notifyDataSetChanged()
                    Log.e("succc", chatAdapter.messageList.last().message)

                }
                Status.LOADING -> {
                    chatAdapter.insertMessage(MessageModel("Thinking...", LOADING_ID,"14:43"))
                    binding.sendCv.isClickable = false
                    binding.inutEditText.isActivated = false
                    binding.inutEditText.isClickable = false
                }

                Status.ERROR -> {
                    customMessage(it.message ?: "error")
                }
            }
        })
    }

    private fun customMessage(message : String){
        val time = Time.timeStamp()
        chatAdapter.insertMessage(MessageModel(message, LOADING_ID, time))
        binding.chatRv.scrollToPosition(chatAdapter.itemCount - 1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}