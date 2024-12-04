package com.example.consulta_facil

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class meus_atestados : AppCompatActivity() {
    lateinit var recy: RecyclerView
    lateinit var searchBar: EditText
    lateinit var btnSearch: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_meus_atestados)

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
            val datasetAtestado = mutableListOf<Atestado>()
            val datasetPrescricao = mutableListOf<Prescricao>()
            val appointmentsRef = fb.collection("usuarios").document(userId).collection("atestados")
            appointmentsRef.get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        datasetAtestado.add( Atestado(
                            id = document.id,
                            nomeMedico = document.getString("doctorName"),
                            specialty = document.getString("description"),
                            data = document.getString("date")
                        ))
                        datasetPrescricao.add( Prescricao(
                            id = document.id,
                            nome = document.getString("name"),
                            dias = document.getString("days"),
                            validade = document.getString("periodicity")
                        ))
                    }

                    recy = findViewById(R.id.RecyclerViewAtestados)
                    val adapterAtestado = AtestadoAdapter(datasetAtestado)
                    val adapterPrescricao = PrescricaoAdapter(datasetPrescricao)
                    recy.layoutManager = LinearLayoutManager(this)
                    recy.adapter = adapterAtestado
                    //recy.adapter = adapterPrescricao
                    adapterAtestado.notifyDataSetChanged()

                    //adapterPrescricao.notifyDataSetChanged()
                }
                .addOnFailureListener { exception ->
                    Log.w("ATESTADOS", "Erro ao buscar atestados: ", exception)
                }
        }
}