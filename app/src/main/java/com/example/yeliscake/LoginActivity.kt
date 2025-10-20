package com.example.yeliscake

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // EditText
        val edtUsuario = findViewById<EditText>(R.id.edtUsuario)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)

        // Botones
        val btnIrRegistro = findViewById<Button>(R.id.btnIrRegistro)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        // Ir a RegisterActivity
        btnIrRegistro.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Validaciones de login
        btnLogin.setOnClickListener {
            val usuario = edtUsuario.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            // Validar usuario
            if (usuario.isEmpty()) {
                edtUsuario.error = "Ingresa tu correo o usuario"
                edtUsuario.requestFocus()
                return@setOnClickListener
            }

            // Validar contraseña
            if (password.isEmpty()) {
                edtPassword.error = "Ingresa tu contraseña"
                edtPassword.requestFocus()
                return@setOnClickListener
            }

            // Validar formato de correo si contiene @
            if (usuario.contains("@") && !Patterns.EMAIL_ADDRESS.matcher(usuario).matches()) {
                edtUsuario.error = "Correo no válido"
                edtUsuario.requestFocus()
                return@setOnClickListener
            }

            // Login exitoso
            Toast.makeText(this, "Bienvenido a Yeli's Cake 🍰", Toast.LENGTH_SHORT).show()
        }
    }
}
