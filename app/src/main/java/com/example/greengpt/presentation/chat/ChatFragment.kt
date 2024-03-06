package com.example.greengpt.presentation.chat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.greengpt.data.dto.remote.dto.MessageDTO
import com.example.greengpt.data.mapper.toContent
import com.example.greengpt.databinding.FragmentChatBinding
import com.example.greengpt.domain.remote.model.ChatPostModel
import com.example.greengpt.domain.remote.model.Message
import com.example.greengpt.util.Resource
import com.example.greengpt.util.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ChatViewModel>()

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
            }
        }

        observeLiveData()

        return binding.root
    }

    fun observeLiveData() {
        viewModel.chatResult.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.d("succcc",it.data.toString())
                }
                Status.LOADING -> {
                    Toast.makeText(requireContext(),"loading",Toast.LENGTH_LONG).show()
                }

                Status.ERROR -> {
                    Log.d("errrr",it.message.toString())
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}