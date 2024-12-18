package com.example.consulta_facil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge

class Menu_medico : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_medico)

        val btNovaConsulta = findViewById<ImageButton>(R.id.button_nova_consulta)
        val btEmitirAtestadoVacina = findViewById<ImageButton>(R.id.button_emitir_atestados_vacinas)
        val btCadastrarHospital = findViewById<ImageButton>(R.id.button_cadastrar_hospital)

        btNovaConsulta.setOnClickListener {
            val intent = Intent(this, ListaPacientesMedico::class.java)
            intent.putExtra("next_view", "consultas")
            startActivity(intent)
        }

        btEmitirAtestadoVacina.setOnClickListener {
            val intent = Intent(this, ListaPacientesMedico::class.java)
            intent.putExtra("next_view", "vacinas")
            startActivity(intent)
        }

        btCadastrarHospital.setOnClickListener {
            val intent = Intent(this, CadastrarHospitais::class.java)
            startActivity(intent)
        }
    }
}