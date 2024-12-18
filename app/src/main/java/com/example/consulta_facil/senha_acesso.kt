package com.example.consulta_facil

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.get
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.io.path.exists

class senha_acesso : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_senha_acesso)
        val bt_senha = findViewById<Button>(R.id.EntrarSenha)
        val senha = findViewById<EditText>(R.id.SenhaView)
        val cbMenuMedico = findViewById<CheckBox>(R.id.checkBoxMenuMedico)
        val fb = Firebase.firestore

        val idFound = intent.getStringExtra("id")
        val crm = intent.getStringExtra("crm")

        if (crm != null && crm != "") {
            cbMenuMedico.visibility = View.VISIBLE
        }

        bt_senha.setOnClickListener {
            fb.collection("usuarios")
                .document(idFound.toString())
                .get()
                .addOnSuccessListener {
                    doc ->
                    if (doc.get("password") == senha.text.toString()) {
                        // User found! saving user id in session...
                        UserSession.userId = idFound.toString()
                        UserSession.userName = doc.get("user").toString()
                        UserSession.userCpf = doc.get("cpf").toString()

                        if (doc.get("crm") != null && doc.get("crm") != "" && cbMenuMedico.isChecked) {
                            Log.d("TAG", "crm: " + doc.get("crm"))
                            val intent = Intent(this, Menu_medico::class.java)
                            startActivity(intent)
                        } else {
                            val intent = Intent(this, Menu::class.java)
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(this, "Senha incorreta", Toast.LENGTH_SHORT).show()
                    }
            }.addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)// Handle any errors
            }
        }
    }
}