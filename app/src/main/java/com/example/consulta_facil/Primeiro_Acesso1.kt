package com.example.consulta_facil

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Primeiro_Acesso1 : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_primeiro_acesso1)
        val bt_consultas = findViewById<Button>(R.id.bem_vindo_avancar)
        val nome = findViewById<EditText>(R.id.nomeInput)
        var fb = Firebase.firestore

        val userData = intent.getSerializableExtra("userData", DadosUsuario::class.java)

        bt_consultas.setOnClickListener {
            if (nome.text.toString().trim().length < 3) {
                Toast.makeText(this, "Digite um nome válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (userData != null) {
                userData.nome = nome.text.toString()
                val intent = Intent(this, Primeiro_Acesso2::class.java)
                intent.putExtra("userData", userData)
                startActivity(intent)
            } else {}
        }
    }
}