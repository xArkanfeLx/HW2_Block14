package com.example.sqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.fragments.MyNote

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object {
        private val DATABASE_NAME = "MY_NOTE_DATABASE_1"
        private val DATABASE_VERSION = 1
        private var lastKey:Int = 1
        val TABLE_NAME = "product_table"
        val KEY_ID = "id"
        val KEY_NOTE = "note"
        val KEY_DATE = "date"
        val KEY_DO = "false"
    }

    fun getLastKey():Int{
        return lastKey++
    }
    fun setLastKey(id:Int){
        lastKey = id
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE $TABLE_NAME ($KEY_ID INTEGET PRIMARY KEY, $KEY_NOTE TEXT, $KEY_DATE TEXT,$KEY_DO TEXT)")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
    }

    fun addNote(note: MyNote) {
        val values = ContentValues()
        values.put(KEY_ID,note.id)
        values.put(KEY_NOTE,note.note)
        values.put(KEY_DATE,note.date)
        values.put(KEY_DO,note.isDo.toString())
        val db = this.writableDatabase
        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    @SuppressLint("Range", "Recycle")
    fun getAllNotes():MutableList<MyNote>{
        val products: MutableList<MyNote> = mutableListOf()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME",null)
        if(cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val note = cursor.getString(cursor.getColumnIndex(KEY_NOTE))
                val date = cursor.getString(cursor.getColumnIndex(KEY_DATE))
                val isDo = cursor.getString(cursor.getColumnIndex(KEY_DO)).toBoolean()
                products.add(MyNote(id, note, date,isDo))
            } while (cursor.moveToNext())
        }
        return products
    }

    fun updateNote(note: MyNote) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_ID,note.id)
        values.put(KEY_NOTE,note.note)
        values.put(KEY_DATE,note.date)
        values.put(KEY_DO,note.isDo.toString())
        db.update(TABLE_NAME,values,"id=${note.id}",null)
        db.close()
    }

    fun deleteAll(){
        val db = this.writableDatabase
        db.delete(TABLE_NAME,null,null)
        lastKey=1
    }
}