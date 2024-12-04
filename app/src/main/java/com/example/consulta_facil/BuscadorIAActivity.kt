package com.example.consulta_facil

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.ai.client.generativeai.GenerativeModel
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class BuscadorIAActivity : AppCompatActivity() {
    private lateinit var etInput: EditText
    private lateinit var btnSend: Button
    private lateinit var rvChat: RecyclerView
    private val chatMessages = mutableListOf<ChatMessage>()
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscador_ia)

        etInput = findViewById(R.id.et_input)
        btnSend = findViewById(R.id.btn_send)
        rvChat = findViewById(R.id.rv_chat)

        // Configurar RecyclerView
        chatAdapter = ChatAdapter(chatMessages)
        rvChat.layoutManager = LinearLayoutManager(this)
        rvChat.adapter = chatAdapter

        val searchQuery = intent.getStringExtra("search_query")
        if (!searchQuery.isNullOrEmpty()) {
            addMessage(searchQuery, isUser = true)
            // Use uma coroutine para chamar a função suspensa
            lifecycleScope.launch {
                fetchChatbotResponse(searchQuery)
            }
        }


        btnSend.setOnClickListener {
            val userMessage = etInput.text.toString()
            if (userMessage.isNotEmpty()) {
                addMessage(userMessage, isUser = true)
                etInput.text.clear()
                // Fazer a chamada ao modelo Gemini
                lifecycleScope.launch {
                    fetchChatbotResponse(userMessage)
                }
            }
        }
    }

    private fun addMessage(text: String, isUser: Boolean) {
        chatMessages.add(ChatMessage(text, isUser))
        chatAdapter.notifyItemInserted(chatMessages.size - 1)
        rvChat.scrollToPosition(chatMessages.size - 1)
    }

    private suspend fun fetchChatbotResponse(userMessage: String) {
        try {
            val generativeModel = GenerativeModel(
                modelName = "gemini-1.5-flash",
                apiKey = "AIzaSyBJD4ROJopgoZUpduCGCh0MNsLUrC0rKu4" // Substitua com a sua chave de API
            )
            val prompt = "voce é um assistente para um aplicativo de gerenciamento de consultas. seus clientes sao pessoas geralmente de idade elevada e que possuem poucas habilidades tecnologicas. o aplicativo consiste nas funcionalidades: verificar consultas, verificar exames, verificar cirurgias, verificar hospitais proximos, verificar vacinas e emitir atestados e prescricoes. essas funcoes estao organizadas na tela principal, nessa ordem, de cima para baixo, da esquerda para a direita. ha tambem as funcoes de cancelar consulta e ver resultado do exame. com essas informacoes, responda: "
            val response = generativeModel.generateContent(prompt + userMessage)
            addMessage(response.text.toString(), isUser = false)
        } catch (e: Exception) {
            addMessage("Erro ao obter resposta: ${e.message}", isUser = false)
        }
    }
}