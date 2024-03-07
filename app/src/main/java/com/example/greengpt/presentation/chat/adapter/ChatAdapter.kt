package com.example.greengpt.presentation.chat.adapter

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.greengpt.R
import com.example.greengpt.databinding.ChatRowBinding
import com.example.greengpt.domain.local.MessageModel
import com.example.greengpt.util.Constants.LOADING_ID
import com.example.greengpt.util.Constants.RECEIVE_ID
import com.example.greengpt.util.Constants.SEND_ID
import com.example.greengpt.util.animateCharacterByCharacter

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatHolder>() {

    var messageList = mutableListOf<MessageModel>()
    inner class ChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        return ChatHolder(LayoutInflater.from(parent.context).inflate(R.layout.chat_row,parent,false))
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        val currentMessage = messageList[position]

        val sentLayout = holder.itemView.findViewById<LinearLayout>(R.id.sentLayout)
        val receiveCv = holder.itemView.findViewById<CardView>(R.id.receiveCv)
        val sentText = holder.itemView.findViewById<TextView>(R.id.sentText)
        val receiveText = holder.itemView.findViewById<TextView>(R.id.receiveTextView)
        val timeText = holder.itemView.findViewById<TextView>(R.id.timeText)

        when(currentMessage.id){
            SEND_ID -> {
                sentText.text = currentMessage.message
                sentLayout.visibility = View.VISIBLE
                receiveCv.visibility = View.GONE
                timeText.text = currentMessage.time
            }

            LOADING_ID -> {
                receiveText.animateCharacterByCharacter(currentMessage.message,33L)
                sentLayout.visibility = View.GONE
                receiveCv.visibility = View.VISIBLE
            }

            RECEIVE_ID -> {
                receiveText.animateCharacterByCharacter(currentMessage.message,33L)
                sentLayout.visibility = View.GONE
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun insertMessage(message : MessageModel) {
        this.messageList.add(message)
        notifyItemInserted(messageList.size)
        notifyDataSetChanged()
    }
}