package com.example.consulta_facil

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class minhas_consultas : AppCompatActivity() {
    lateinit var recy: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_minhas_consultas)

        val dataset = listOf<String>("Narak","Huan","Alberto","Estrogolosopeudo","Heric","Henrrique","Maia","Jubiscleudo"
            ,"Ernesto","Brandao","Atila","Pedro","Joao P.")
        recy = findViewById(R.id.RecyclerViewConsultas)
        val adapter = MyAdapter(dataset)
        recy.layoutManager = LinearLayoutManager(this)
        recy.adapter = adapter
    }
}