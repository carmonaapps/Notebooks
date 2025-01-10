package com.example.notebook

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.example.notebook.databinding.ActivityMainBinding
import com.example.notebook.data.Note
import com.example.notebook.data.NoteDatabase
import com.example.notebook.data.NoteRepository
import com.example.notebook.ui.EditNoteActivity
import com.example.notebook.ui.NoteAdapter
import com.example.notebook.ui.NoteViewModel
import com.example.notebook.ui.NoteViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NoteViewModel
    private lateinit var adapter: NoteAdapter
    
    private val editNoteLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.getParcelableExtra<Note>("note")?.let { note ->
                viewModel.insert(note)
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Initialize App Center
        AppCenter.start(
            application,
            "YOUR-APP-CENTER-KEY",
            Analytics::class.java,
            Crashes::class.java
        )
        
        // Initialize ViewModel
        val dao = NoteDatabase.getDatabase(application).noteDao()
        val repository = NoteRepository(dao)
        val factory = NoteViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]
        
        setupUI()
        observeNotes()
    }
    
    private fun setupUI() {
        adapter = NoteAdapter { note ->
            openEditNote(note)
        }
        
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
        
        binding.fab.setOnClickListener {
            openEditNote(null)
        }
    }
    
    private fun observeNotes() {
        viewModel.allNotes.observe(this) { notes ->
            adapter.submitList(notes)
        }
    }
    
    private fun openEditNote
