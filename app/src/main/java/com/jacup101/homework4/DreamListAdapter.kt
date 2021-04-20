package com.jacup101.homework4

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


class DreamListAdapter(context : Context) : ListAdapter<Dream, DreamListAdapter.DreamViewHolder>(DreamComparator()) {

    val context : Context = context


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DreamListAdapter.DreamViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dream, parent, false)
        return DreamViewHolder(view)
    }

    override fun onBindViewHolder(holder: DreamListAdapter.DreamViewHolder, position: Int) {
        val currentDream = getItem(position)

        holder.bindText("" + currentDream.id, holder.idTextView)
        holder.bindText("" + currentDream.title, holder.dreamTextView)


    }





    inner class DreamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView : TextView = itemView.findViewById(R.id.textView_dreamID)
        val dreamTextView : TextView = itemView.findViewById(R.id.textView_dreamTitleItem)
        init {
            itemView.setOnClickListener {
                //Log.d("item clicked", "" + adapterPosition)
                val intent = Intent(context,DreamActivity::class.java)
                var dream = getItem(adapterPosition)
                //Log.d("dream_activity_put","" + dream.id)
                intent.putExtra("id",dream.id)
                context.startActivity(intent)

            }
        }

        fun bindText(text : String?, textView : TextView) {
            textView.text = text
        }

    }
    class DreamComparator : DiffUtil.ItemCallback<Dream>() {
        override fun areItemsTheSame(oldItem: Dream, newItem: Dream): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Dream, newItem: Dream): Boolean {
            return oldItem.id == newItem.id
        }

    }

}