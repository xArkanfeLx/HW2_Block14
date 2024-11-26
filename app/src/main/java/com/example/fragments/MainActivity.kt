package com.example.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sqlite.DBHelper

class MainActivity : AppCompatActivity(), OnFragmentsDataListener {

    private val db = DBHelper(this, null)
    var notes: MutableList<MyNote> = mutableListOf()
    private var toUpdate = true

    private lateinit var toolbarTB: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
    }

    @SuppressLint("CommitTransaction")
    private fun init() {
        toolbarTB = findViewById(R.id.toolbarTB)
        setSupportActionBar(toolbarTB)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, NotesFragment())
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (!toUpdate) {
            showNewFragment(toUpdate, Bundle())
            toUpdate=true
        } else finish()
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("CommitTransaction")
    override fun onData(data: MyNote?) {
        val bundle = Bundle()
        bundle.putSerializable("note", data)

        showNewFragment(toUpdate, bundle)
    }

    fun showNewFragment(isUpdate: Boolean, bundle: Bundle) {
        val transaction = this.supportFragmentManager.beginTransaction()
        var newFragment: Fragment
        if (isUpdate) newFragment = UpdateFragment()
        else newFragment = NotesFragment()
        toUpdate = !isUpdate
        newFragment.arguments = bundle
        transaction.replace(R.id.fragment_container, newFragment)
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }
}