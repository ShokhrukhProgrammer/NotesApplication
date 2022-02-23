package uz.shoxsoft.notesapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import uz.shoxsoft.notesapplication.adapter.NoteClickDeleteInterface
import uz.shoxsoft.notesapplication.adapter.NoteClickInterface
import uz.shoxsoft.notesapplication.adapter.NoteRVAdapter
import uz.shoxsoft.notesapplication.databinding.ActivityMainBinding
import uz.shoxsoft.notesapplication.model.Note
import uz.shoxsoft.notesapplication.viewModel.NoteViewModel

class MainActivity : AppCompatActivity(), NoteClickInterface, NoteClickDeleteInterface {
    private lateinit var binding: ActivityMainBinding
    private lateinit var notesRV: RecyclerView
    private lateinit var addFAB: FloatingActionButton
    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notesRV = binding.idRVNotes
        addFAB = binding.idFABAddNote

        notesRV.layoutManager = LinearLayoutManager(this)

        val noteRvAdapter = NoteRVAdapter(this, this, this)
        notesRV.adapter = noteRvAdapter
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModel::class.java)
        viewModel.allNote.observe(this, { list ->
            list?.let {
                noteRvAdapter.updateList(it)
            }
        })
        addFAB.setOnClickListener {
            val intent = Intent(this, AddEditNoteActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    override fun onDeleteIconClick(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this, "${note.noteTitle} Deleted", Toast.LENGTH_LONG).show()
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this, AddEditNoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.noteTitle)
        intent.putExtra("noteDescription", note.noteDescription)
        intent.putExtra("noteID", note.id)
        startActivity(intent)
        this.finish()
    }
}