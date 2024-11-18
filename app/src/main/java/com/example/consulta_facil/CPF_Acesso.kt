package com.example.consulta_facil

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class CPF_Acesso : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cpf_acesso)

        val fb = Firebase.firestore
        val botaoAvancar = findViewById<Button>(R.id.cadastrar)
        val cpfCampo = findViewById<EditText>(R.id.CPFView)

        botaoAvancar.setOnClickListener{
            if(cpfCampo.text.toString().trim().length != 11) {
                Toast.makeText(this, "Digite um CPF válido", Toast.LENGTH_SHORT).show();
                return@setOnClickListener
            }
            Toast.makeText(this, "Checando CPF...", Toast.LENGTH_SHORT).show()

            //Toast.makeText(this, cpfCampo.text.toString(), Toast.LENGTH_SHORT).show()
            fb.collection("usuarios").get()
                .addOnSuccessListener { docs ->
                    var idFound = ""
                    for (doc in docs){
                        if(doc.get("cpf")==cpfCampo.text.toString()){
                            idFound = doc.id.toString()
                            break
                        }
                    }
                    if (idFound == "") {
                        val intent = Intent(this, Primeiro_Acesso1::class.java)
                        intent.putExtra("cpf", cpfCampo.text.toString())
                        startActivity(intent)
                    } else {
                        val intent = Intent(this, senha_acesso::class.java)
                        intent.putExtra("id", idFound)
                        startActivity(intent)
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
}

private fun Intent.putExtra(s: String, data: Map<String, Any>) {

}