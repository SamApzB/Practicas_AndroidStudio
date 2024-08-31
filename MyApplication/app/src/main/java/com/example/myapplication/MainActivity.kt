package com.example.myapplication
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.nl.smartreply.SmartReply
import com.google.mlkit.nl.smartreply.SmartReplySuggestionResult
import com.google.mlkit.nl.smartreply.TextMessage

class MainActivity : AppCompatActivity() {
    private var conversation = ArrayList<TextMessage>()

    private lateinit var sendButton: Button
    private lateinit var hintsButton: Button
    private lateinit var clearButton: Button
    private lateinit var hint0Button: Button
    private lateinit var hint1Button: Button
    private lateinit var hint2Button: Button
    private lateinit var messageText: EditText
    private lateinit var nameText: EditText
    private lateinit var errorText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicialización de los elementos del layout
        sendButton = findViewById(R.id.sendButton)
        hintsButton = findViewById(R.id.hintsButton)
        clearButton = findViewById(R.id.clearButton)
        hint0Button = findViewById(R.id.hint0Button)
        hint1Button = findViewById(R.id.hint1Button)
        hint2Button = findViewById(R.id.hint2Button)
        messageText = findViewById(R.id.messageText)
        nameText = findViewById(R.id.nameText)
        errorText = findViewById(R.id.errorText)

        sendButton.setOnClickListener {
            addMessage(messageText.text.toString())
        }

        hintsButton.setOnClickListener {
            getHints()
        }

        clearButton.setOnClickListener {
            clearConversation()
        }

        hint0Button.setOnClickListener {
            addMessage(hint0Button.text.toString())
        }

        hint1Button.setOnClickListener {
            addMessage(hint1Button.text.toString())
        }

        hint2Button.setOnClickListener {
            addMessage(hint2Button.text.toString())
        }
    }

    private fun addMessage(text: String) {
        conversation.add(TextMessage.createForRemoteUser(
            text, System.currentTimeMillis(), nameText.text.toString()))
    }

    private fun getHints() {
        val smartReply = SmartReply.getClient()
        smartReply.suggestReplies(conversation)
            .addOnSuccessListener { result ->
                if (result.status == SmartReplySuggestionResult.STATUS_NOT_SUPPORTED_LANGUAGE) {
                    Toast.makeText(applicationContext, "Lenguaje no soportado", Toast.LENGTH_SHORT).show()
                } else if (result.status == SmartReplySuggestionResult.STATUS_SUCCESS) {
                    hint0Button.text = result.suggestions[0].text
                    hint1Button.text = result.suggestions[1].text
                    hint2Button.text = result.suggestions[2].text
                    hint0Button.visibility = View.VISIBLE
                    hint1Button.visibility = View.VISIBLE
                    hint2Button.visibility = View.VISIBLE
                }
            }
            .addOnFailureListener {
                errorText.text = it.toString()
            }
    }

    private fun clearConversation() {
        conversation.clear()
        hint0Button.visibility = View.GONE
        hint1Button.visibility = View.GONE
        hint2Button.visibility = View.GONE
    }
}
