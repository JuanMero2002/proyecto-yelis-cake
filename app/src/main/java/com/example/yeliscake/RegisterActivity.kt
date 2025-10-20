package com.example.yeliscake

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val edtNombre = findViewById<EditText>(R.id.edtNombre)
        val edtDireccion = findViewById<EditText>(R.id.edtDireccion)
        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtPassword = findViewById<EditText>(R.id.edtPasswordRegistro)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        btnRegistrar.setOnClickListener {
            val nombre = edtNombre.text.toString().trim()
            val direccion = edtDireccion.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            // Validación de nombre
            if (nombre.isEmpty()) {
                Toast.makeText(this, "Ingrese su nombre", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validación de dirección
            if (direccion.isEmpty() || direccion.length < 5) {
                Toast.makeText(this, "Ingrese una dirección válida", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validación de email
            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Ingrese un correo válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validación de contraseña
            if (password.isEmpty() || password.length < 6) {
                Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Si todo está bien
            Toast.makeText(this, "Usuario registrado exitosamente 🎉", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
