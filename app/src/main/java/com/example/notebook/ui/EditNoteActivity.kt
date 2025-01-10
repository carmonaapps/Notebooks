package com.example.notebook.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.notebook.R
import com.example.notebook.data.Note
import com.example.notebook.databinding.ActivityEditNoteBinding

class EditNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditNoteBinding
    private var currentNote: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        currentNote = intent.getParcelableExtra("note")
        currentNote?.let {
            binding.titleEditText.setText(it.title)
            binding.contentEditText.setText(it.content)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_edit_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                saveNoteAndFinish()
                true
            }
            R.id.action_save -> {
                saveNoteAndFinish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveNoteAndFinish() {
        val title = binding.titleEditText.text.toString().takeIf { it.isNotBlank() } ?: "Untitled"
        val content = binding.contentEditText.text.toString()
        
        val note = currentNote?.copy(title = title, content = content) ?: 
                   Note(title = title, content = content)
        
        setResult(RESULT_OK, intent.putExtra("note", note))
        finish()
    }
}
