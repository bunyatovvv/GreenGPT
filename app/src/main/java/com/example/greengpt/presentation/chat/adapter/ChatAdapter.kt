package com.example.greengpt.presentation.chat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.greengpt.R
import com.example.greengpt.domain.model.local.MessageModel
import com.example.greengpt.util.Constants.FIRST_ID
import com.example.greengpt.util.Constants.LOADING_ID
import com.example.greengpt.util.Constants.RECEIVE_ID
import com.example.greengpt.util.Constants.SEND_ID
import com.example.greengpt.util.animateCharacterByCharacter
import com.example.greengpt.util.copyToClipboard
import com.example.greengpt.util.gone
import com.example.greengpt.util.visible

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatHolder>() {

    var messageList = mutableListOf<MessageModel>()

    inner class ChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        return ChatHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.chat_row, parent, false)
        )
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
        val copyIcon = holder.itemView.findViewById<ImageView>(R.id.copyImage)
        val likeLayout = holder.itemView.findViewById<LinearLayout>(R.id.likeLayout)
        val copyLayout = holder.itemView.findViewById<LinearLayout>(R.id.copyLayout)
        val options = holder.itemView.findViewById<ImageView>(R.id.options)


        when (currentMessage.id) {

            FIRST_ID -> {
                sentLayout.gone()
                copyLayout.gone()
                likeLayout.gone()
            }

            SEND_ID -> {
                sentText.text = currentMessage.message
                sentLayout.visible()
                receiveCv.gone()
                timeText.text = currentMessage.time
            }

            LOADING_ID -> {
                receiveText.animateCharacterByCharacter(currentMessage.message, 33L)
                sentLayout.gone()
                receiveCv.visible()
                likeLayout.visible()
                copyLayout.visible()
            }

            RECEIVE_ID -> {
                receiveText.animateCharacterByCharacter(currentMessage.message, 33L)
                sentLayout.gone()
                likeLayout.visible()
                copyLayout.visible()
            }
        }

        copyIcon.setOnClickListener {
            it.context.copyToClipboard(receiveText.text)
        }

        options.setOnClickListener {

            val popupMenu = PopupMenu(holder.itemView.context, it)
            popupMenu.inflate(R.menu.popup_menu)

            popupMenu.setOnMenuItemClickListener {


                when (it.itemId) {

                    R.id.save -> {
                        saveItem?.let {
                            it(currentMessage)
                        }
                        true
                    }

                    R.id.share -> {
                        shareItem?.let {
                            it(currentMessage)
                        }
                        true
                    }

                    else -> {
                        false
                    }
                }
            }
            popupMenu.show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun insertMessage(message: MessageModel) {
        this.messageList.add(message)
        notifyItemInserted(messageList.size)
        notifyDataSetChanged()
    }

    private var shareItem : ((MessageModel) -> Unit)? = null

    private var saveItem : ((MessageModel) -> Unit)? = null

    fun setOnSaveClickedListener(listener: (MessageModel) -> Unit){
        saveItem = listener
    }

    fun setOnShareClickedListener(listener: (MessageModel) -> Unit){
        shareItem = listener
    }




}