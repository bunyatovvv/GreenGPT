package com.example.greengpt.presentation.chat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.greengpt.data.dto.remote.dto.MessageDTO
import com.example.greengpt.data.mapper.toContent
import com.example.greengpt.databinding.FragmentChatBinding
import com.example.greengpt.domain.local.MessageModel
import com.example.greengpt.domain.remote.model.ChatPostModel
import com.example.greengpt.domain.remote.model.Message
import com.example.greengpt.presentation.chat.adapter.ChatAdapter
import com.example.greengpt.util.Constants.LOADING_ID
import com.example.greengpt.util.Constants.RECEIVE_ID
import com.example.greengpt.util.Constants.SEND_ID
import com.example.greengpt.util.Resource
import com.example.greengpt.util.Status
import com.example.greengpt.util.Time
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ChatViewModel>()
    private val chatAdapter by lazy { ChatAdapter() }

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



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        observeLiveData()
        val time = Time.timeStamp()
        val messageModel = MessageModel("Hi, how can i help you?", RECEIVE_ID,time)
        chatAdapter.insertMessage(messageModel)

        super.onViewCreated(view, savedInstanceState)
    }

    fun observeLiveData() {
        viewModel.chatResult.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data!!.choices.map {
                        customMessage(it.message.content)
                    }
                    Log.d("succc",it.data.toString())
                }
                Status.LOADING -> {
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
        chatAdapter.insertMessage(MessageModel(message, RECEIVE_ID, time))
        binding.chatRv.scrollToPosition(chatAdapter.itemCount - 1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}