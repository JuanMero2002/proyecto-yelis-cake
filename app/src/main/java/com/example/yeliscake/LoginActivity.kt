package com.example.yeliscake

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.yeliscake.database.AppDatabase
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val edtUsuario = findViewById<EditText>(R.id.edtUsuario)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val btnIrRegistro = findViewById<Button>(R.id.btnIrRegistro)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnVerUsuarios = findViewById<Button>(R.id.btnVerUsuarios)

        // Ir a RegisterActivity
        btnIrRegistro.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Ver lista de usuarios
        btnVerUsuarios.setOnClickListener {
            val intent = Intent(this, ListaUsuariosActivity::class.java)
            startActivity(intent)
        }

        // Login
        btnLogin.setOnClickListener {
            val usuario = edtUsuario.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            // Validar usuario
            if (usuario.isEmpty()) {
                edtUsuario.error = "Ingresa tu correo"
                edtUsuario.requestFocus()
                return@setOnClickListener
            }

            // Validar contraseña
            if (password.isEmpty()) {
                edtPassword.error = "Ingresa tu contraseña"
                edtPassword.requestFocus()
                return@setOnClickListener
            }

            // Validar formato de correo
            if (!Patterns.EMAIL_ADDRESS.matcher(usuario).matches()) {
                edtUsuario.error = "Correo no válido"
                edtUsuario.requestFocus()
                return@setOnClickListener
            }

            // Verificar credenciales en Room
            val dao = AppDatabase.getInstance(this).usuarioDao()

            lifecycleScope.launch {
                val usuarioEncontrado = dao.buscarPorEmail(usuario)

                runOnUiThread {
                    if (usuarioEncontrado != null && usuarioEncontrado.password == password) {
                        Toast.makeText(
                            this@LoginActivity,
                            "¡Bienvenid@ ${usuarioEncontrado.nombre}! 🍰",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Aquí podrías navegar a una pantalla principal
                        // startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Credenciales incorrectas",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}