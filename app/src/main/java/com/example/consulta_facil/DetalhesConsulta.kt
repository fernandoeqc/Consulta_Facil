package com.example.consulta_facil

import android.content.Intent
import android.widget.Button

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DetalhesConsulta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var searchBar: EditText
        lateinit var btnSearch: Button
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalhes_consulta)

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

        val buttonCancelarConsulta = findViewById<Button>(R.id.button_cancelar)

        val fb = Firebase.firestore
        val consulta = intent.getParcelableExtra<Consulta>("consulta")

        if (consulta == null) {
            Toast.makeText(this, "Consulta não encontrada", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        //alterar nomes de campos
        val campoNomeMedico = findViewById<TextView>(R.id.consulta_medico)
        val campoEspecialidade = findViewById<TextView>(R.id.consulta_nome)
        val campoData = findViewById<TextView>(R.id.consulta_data)
        val campoEndereco = findViewById<TextView>(R.id.consulta_endereço)

        campoNomeMedico.text = consulta.nomeMedico
        campoEspecialidade.text = consulta.especialidade
        campoData.text = consulta.data

        Log.d("DETALHES_CONSULTA", consulta.id.toString())

        fb.collection("usuarios").document(consulta.id.toString()).get()
            .addOnSuccessListener { doc ->
                if (doc != null && doc.exists()) {
                    val endereco = doc.getString("address").toString()
                    Log.d("DETALHES_CONSULTA", endereco)
                    campoEndereco.text = endereco
                }
                else {
                    Log.d("DETALHES_CONSULTA", "No such document")
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(this, "erro", Toast.LENGTH_SHORT).show()
                println(exception.message)
                println(exception.stackTrace)
                println(exception.localizedMessage)
                println(exception.cause)
            }

        buttonCancelarConsulta.setOnClickListener{
            val intent = Intent(this, cancelar_tela::class.java)
            intent.putExtra("consulta", consulta)
            startActivity(intent)
        }
    }
}