package com.example.consulta_facil

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton


class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var searchBar: EditText
        lateinit var btnSearch: Button
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

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

        val bt_consultas = findViewById<ImageButton>(R.id.button_minhas_consultas)
        bt_consultas.setOnClickListener {
            val intent = Intent(this, minhasConsultas::class.java)
            startActivity(intent)
        }

        val bt_exames = findViewById<ImageButton>(R.id.button_meus_exames)
        bt_exames.setOnClickListener {
            val intent = Intent(this, meus_exames::class.java)
            startActivity(intent)
        }

        val bt_cirurgias = findViewById<ImageButton>(R.id.button_minhas_cirurgias)
        bt_cirurgias.setOnClickListener {
            val intent = Intent(this, Minhas_cirurgias::class.java)
            startActivity(intent)
        }

        val bt_upas_proximas = findViewById<ImageButton>(R.id.button_upas_proximas)
        bt_upas_proximas.setOnClickListener {
            val intent = Intent(this, upas_proximas::class.java)
            startActivity(intent)
        }

        val bt_vacinas = findViewById<ImageButton>(R.id.button_minhas_vacinas)
        bt_vacinas.setOnClickListener {
            val intent = Intent(this, menu_vacinas::class.java)
            startActivity(intent)
        }

        val bt_atestados = findViewById<ImageButton>(R.id.button_atestados_prescricoes)
        bt_atestados.setOnClickListener {
            val intent = Intent(this, meus_atestados::class.java)
            startActivity(intent)
        }
    }
}