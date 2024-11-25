package com.example.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlite.DBHelper
import java.util.Date

class NotesFragment : Fragment() {

    private var notes:MutableList<MyNote> = mutableListOf()
    private lateinit var recyclerRV:RecyclerView
    private var db:DBHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = this.context?.let { DBHelper(it, null) }
        notes = db!!.getAllNotes()
        db!!.setLastKey(notes.size+1)
        val noteET = view.findViewById<EditText>(R.id.noteET)
        val addBTN = view.findViewById<Button>(R.id.addBTN)
        recyclerRV = view.findViewById(R.id.recyclerRV)
        reInitAdapter()

        addBTN.setOnClickListener{
            val note = noteET.text
            if(note.isNotEmpty()) {
                val date = Date().toString()
                val myNote = MyNote(db!!.getLastKey(),note.toString(),date,false)
                notes.add(myNote)
                db!!.addNote(myNote)
                reInitAdapter()
                noteET.text.clear()
            }
        }
    }

    private fun reInitAdapter(){
        recyclerRV.layoutManager = LinearLayoutManager(this.context)
        recyclerRV.adapter = this.context?.let { CustomAdapter(it,notes) }
    }
}