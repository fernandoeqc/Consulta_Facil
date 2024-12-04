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

class DetalhesVacina : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var searchBar: EditText
        lateinit var btnSearch: Button
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalhes_vacina)

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

        val fb = Firebase.firestore
        val vacina = intent.getParcelableExtra<Vacina>("vacina")

        if (vacina == null) {
            Toast.makeText(this, "Vacina não encontrada", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        //alterar nomes de campos
        val campoNomeMedico = findViewById<TextView>(R.id.vacina_medico)
        val campoEspecialidade = findViewById<TextView>(R.id.vacina_nome)
        val campoData = findViewById<TextView>(R.id.vacina_data)

        campoNomeMedico.text = vacina.nomeMedico
        campoEspecialidade.text = vacina.specialty
        campoData.text = vacina.data

        Log.d("DETALHES_VACINA", vacina.id.toString())

        fb.collection("usuarios").document(vacina.id.toString()).get()
            .addOnSuccessListener { doc ->
                if (doc != null && doc.exists()) {
                    val endereco = doc.getString("address").toString()
                    Log.d("DETALHES_VACINA", endereco)
                }
                else {
                    Log.d("DETALHES_VACINA", "No such document")
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(this, "erro", Toast.LENGTH_SHORT).show()
                println(exception.message)
                println(exception.stackTrace)
                println(exception.localizedMessage)
                println(exception.cause)
            }
    }
}