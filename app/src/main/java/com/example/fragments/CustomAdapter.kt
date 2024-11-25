package com.example.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlite.DBHelper

class CustomAdapter(private val context: Context, private val notes: MutableList<MyNote>) :
    RecyclerView.Adapter<CustomAdapter.ItemViewHolder>() {
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTV: TextView = itemView.findViewById(R.id.idTV)
        val noteTV: TextView = itemView.findViewById(R.id.noteTV)
        val dateTV: TextView = itemView.findViewById(R.id.dateTV)
        val checkbox: CheckBox = itemView.findViewById(R.id.checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate((R.layout.note_item), parent, false)
        return ItemViewHolder(itemView)
    }

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val db = DBHelper(context,null)
        val note = notes[position]
        holder.idTV.text = note.id.toString()
        holder.noteTV.text = note.note
        holder.dateTV.text = note.date
        holder.checkbox.isChecked = note.isDo

        holder.checkbox.setOnClickListener{
            db.updateProduct(MyNote(note.id,note.note,note.date,holder.checkbox.isChecked))
        }
    }
}