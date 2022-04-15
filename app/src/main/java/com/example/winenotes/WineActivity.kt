package com.example.winenotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import com.example.winenotes.database.AppDatabase
import com.example.winenotes.database.NOTE
import com.example.winenotes.databinding.ActivityWineBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class WineActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWineBinding

    private var purpose : String? = ""
    private var noteId : Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = getIntent()
        purpose = intent.getStringExtra(
            getString(R.string.intent_purpose_key)
        )

        if(purpose.equals(getString(R.string.intent_purpose_update_note))) {
            noteId = intent.getLongExtra(
                getString(R.string.intent_key_note_id),
                        -1
            )

            CoroutineScope(Dispatchers.IO).launch {
                val note = AppDatabase.getDatabase(applicationContext)
                    .noteDao()
                    .getNote(noteId)

                withContext(Dispatchers.Main) {
                    binding.titleEditText.setText(note.title)
                    binding.editTextTextMultiLine.setText(note.notesEntered)
                    binding.lastModifiedTextView.setText(note.lastModified)
                }
            }
        }

        setTitle("${purpose} Note")
    }

    override fun onBackPressed() {
        val title = binding.titleEditText.getText().toString().trim().capitalize()
        if (title.isEmpty()) {
            Toast.makeText(applicationContext,
            "Title cannot be empty", Toast.LENGTH_LONG).show()
            return
        }
        val note = binding.editTextTextMultiLine.getText().toString().trim().capitalize()
        val now : Date = Date()
        val databaseDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        databaseDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"))
        var dateString : String = databaseDateFormat.format(now)

        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        parser.setTimeZone(TimeZone.getTimeZone("UTC"))
        val dateInDatabase : Date = parser.parse(dateString)
        val displayFormat = SimpleDateFormat("HH:mm a MM/yyyy ")
        val displayString: String = displayFormat.format(dateInDatabase)

        CoroutineScope(Dispatchers.IO).launch {
            val noteDao = AppDatabase.getDatabase(applicationContext)
                .noteDao()

            if (purpose.equals(getString(R.string.intent_purpose_add_note))) {
                val note = NOTE(0, title, note,displayString)
                noteId = noteDao.addNote(note)
                Log.i("STATUS_NOTE", "inserted new note: ${note}")
            } else {
                // update current note in the database
                val note = NOTE(noteId, title, note, displayString)
                noteDao.updateNote(note)
                Log.i("STATUS_NOTE", "updated existing note: ${note}")
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