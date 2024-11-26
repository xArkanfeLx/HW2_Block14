package com.example.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentTransaction
import com.example.sqlite.DBHelper
import java.util.Date

class UpdateFragment : Fragment() {

    private var db: DBHelper? = null
    private var editNnote:MyNote? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        editNnote = arguments?.getSerializable("note") as MyNote
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = this.context?.let { DBHelper(it, null) }

        val noteET = view.findViewById<EditText>(R.id.noteET)
        val updateBTN = view.findViewById<Button>(R.id.updateBTN)

        noteET.setText(editNnote!!.note)

        updateBTN.setOnClickListener{
            val note = noteET.text
            if(note.isNotEmpty()) {
                val myNote = MyNote(editNnote!!.id,note.toString(),editNnote!!.date,editNnote!!.isDo)
                db!!.updateNote(myNote)

                (activity as MainActivity).showNewFragment(false,Bundle())
            }
        }
    }
}