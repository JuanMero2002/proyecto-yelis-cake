package com.example.yeliscake

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnIrRegistro = findViewById<Button>(R.id.btnIrRegistro)
        val btnVerUsuarios = findViewById<Button>(R.id.btnVerUsuarios)
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)

        // Ir a registro
        btnIrRegistro.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // Ver lista de usuarios
        btnVerUsuarios.setOnClickListener {
            startActivity(Intent(this, ListaUsuariosActivity::class.java))
        }

        // Cerrar sesión y volver al login
        btnCerrarSesion.setOnClickListener {
            finish()
        }
    }
}