package com.example.winenotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.winenotes.database.AppDatabase
import com.example.winenotes.database.NOTE
import com.example.winenotes.databinding.ActivityWineBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WineActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWineBinding

    private var purpose : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = getIntent()
        purpose = intent.getStringExtra(
            getString(R.string.intent_purpose_key)
        )
        setTitle("${purpose} Note")
    }

    override fun onBackPressed() {
        val title = binding.titleEditText.getText().toString().trim()
        if (title.isEmpty()) {
            Toast.makeText(applicationContext,
            "Title cannot be empty", Toast.LENGTH_LONG).show()
            return
        }
        val note = binding.editTextTextMultiLine.getText().toString().trim()

        CoroutineScope(Dispatchers.IO).launch {
            val noteDao = AppDatabase.getDatabase(applicationContext)
                .noteDao()

            val noteId : Long

            if (purpose.equals(getString(R.string.intent_purpose_add_note))) {
                val note = NOTE(0, title, note)
                noteId = noteDao.addNote(note)
                Log.i("STATUS_NOTE", "inserted new note: ${note}")
            } else {
                // update current note in the database
                TODO("Not implemented")
            }

        val intent = Intent()

            intent.putExtra(
                getString(R.string.intent_key_note_id),
                noteId
            )

            withContext(Dispatchers.Main) {
                setResult(RESULT_OK, intent)
                super.onBackPressed()
            }
        }
    }
}