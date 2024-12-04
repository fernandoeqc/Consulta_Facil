package com.example.consulta_facil

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DetalhesPrescricao : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var searchBar: EditText
        lateinit var btnSearch: Button
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalhes_atestado)

        searchBar = findViewById(R.id.search_bar)
        btnSearch = findViewById(R.id.btn_search)

        btnSearch.setOnClickListener {
            val inputText = searchBar.text.toString()
            if (inputText.isNotEmpty()) {
                // Criar intent para navegar para a tela do chatbot
                val intent = Intent(this, BuscadorIAActivity::class.java)
                intent.putExtra("search_query", inputText)
                startActivity(intent)
            }
        }

        val buttonEmitirResultado = findViewById<Button>(R.id.button_imprimir)

        val fb = Firebase.firestore
        val prescricao = intent.getParcelableExtra<Prescricao>("prescricao")
        var nomePrescricao = ""

        if (prescricao == null) {
            Toast.makeText(this, "Prescricao não encontrado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        //alterar nomes de campos
        val campoNomeMedico = findViewById<TextView>(R.id.text_nome_medico_at)
        val campoData = findViewById<TextView>(R.id.text_data_at)
        val campoNomePrescricao = findViewById<TextView>(R.id.nome_atestado)

        if (campoNomeMedico == null || campoData == null || campoNomePrescricao == null) {
            Toast.makeText(this, "informação não encontrado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        campoNomeMedico.text = prescricao.nome
        campoData.text = prescricao.dias
        campoData.text = prescricao.validade

        Log.d("DETALHES_ATESTADO", prescricao.id.toString())
        Log.d("DATA:", prescricao.dias.toString())
        Log.d("SPECIALTY:", prescricao.validade.toString())

        fb.collection("usuarios").document(prescricao.id.toString()).get()
            .addOnSuccessListener { doc ->
                if (doc != null && doc.exists()) {
                    nomePrescricao = doc.getString("specialty").toString()
                    Log.d("DETALHES_ATESTADO", nomePrescricao)
                    campoNomePrescricao.text = nomePrescricao
                }
                else {
                    Log.d("DETALHES_ATESTADO", "No such document")
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(this, "erro", Toast.LENGTH_SHORT).show()
                println(exception.message)
                println(exception.stackTrace)
                println(exception.localizedMessage)
                println(exception.cause)
            }

        buttonEmitirResultado.setOnClickListener{
            Toast.makeText(this, "Imprimindo Prescrição", Toast.LENGTH_SHORT).show()
        }
    }
}