package com.example.greengpt.presentation.saved.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.greengpt.R
import com.example.greengpt.data.dto.local.FavoriteDTO
import com.google.android.material.snackbar.Snackbar

class SavedAdapter : RecyclerView.Adapter<SavedAdapter.SavedHolder>() {

    inner class SavedHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

    }

    private val differCallBack = object : DiffUtil.ItemCallback<FavoriteDTO>() {
        override fun areItemsTheSame(oldItem: FavoriteDTO, newItem: FavoriteDTO): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: FavoriteDTO, newItem: FavoriteDTO): Boolean {
            return oldItem.id == newItem.id
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)
    var favorite: List<FavoriteDTO>
        get() = differ.currentList
        set(value) = differ.submitList(value.toList())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedHolder {
        return SavedHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.saved_row, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return favorite.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: SavedHolder, position: Int) {
        val favorites = differ.currentList[position]

        val title = holder.itemView.findViewById<TextView>(R.id.savedTitleText)
        val des = holder.itemView.findViewById<TextView>(R.id.savedDescriptionText)

        title.text = favorites.title
        des.text = favorites.message

        holder.itemView.setOnLongClickListener {

            val snackbar = Snackbar.make(it,"Do you want to delete it from saved list?", Snackbar.LENGTH_LONG).setAction("Yes"){
                yesClicked?.let {
                    it(favorites)
                    notifyDataSetChanged()
                }
            }

            snackbar.show()

            onItemLongClicked?.let {
                it(favorites)
            }
            true
        }
    }

    private var onItemLongClicked : ((FavoriteDTO) -> Unit)? = null
    private var yesClicked : ((FavoriteDTO) -> Unit)? = null

    fun setOnLongClickedItem(listener: (FavoriteDTO) -> Unit){
        onItemLongClicked = listener
    }

    fun setOnYesClicked(listener: (FavoriteDTO) -> Unit){
       yesClicked = listener
    }



}