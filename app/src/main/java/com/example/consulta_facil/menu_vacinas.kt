package com.example.consulta_facil

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class menu_vacinas : AppCompatActivity() {
    lateinit var searchBar: EditText
    lateinit var btnSearch: Button
    lateinit var recy: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_vacinas)

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

        getUserAppointments(UserSession.userId.toString())
    }

    private fun getUserAppointments(userId: String) {
        val fb = Firebase.firestore
        val dataset = mutableListOf<Vacina>()
        val appointmentsRef = fb.collection("usuarios").document(userId).collection("vacinas")
        appointmentsRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    dataset.add( Vacina(
                        id = document.id,
                        nomeMedico = document.getString("doctorName"),
                        specialty = document.getString("nome"),
                        data = document.getString("data")
                    ))
                }

                recy = findViewById(R.id.RecyclerViewVacinas)
                val adapter = VacinaAdapter(dataset)
                recy.layoutManager = LinearLayoutManager(this)
                recy.adapter = adapter
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w("VACINAS", "Erro ao buscar vacinas: ", exception)
            }
    }
}