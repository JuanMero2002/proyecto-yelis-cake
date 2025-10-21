package com.example.yeliscake

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.yeliscake.cloud.FirebaseService
import com.example.yeliscake.database.AppDatabase
import com.example.yeliscake.model.UsuarioEntity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private var fechaSeleccionada: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val edtNombre = findViewById<EditText>(R.id.edtNombre)
        val edtFechaNacimiento = findViewById<EditText>(R.id.edtFechaNacimiento)
        val edtDireccion = findViewById<EditText>(R.id.edtDireccion)
        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtPassword = findViewById<EditText>(R.id.edtPasswordRegistro)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        // Abrir selector de fecha
        edtFechaNacimiento.setOnClickListener {
            val hoy = Calendar.getInstance()
            val anio = hoy.get(Calendar.YEAR)
            val mes = hoy.get(Calendar.MONTH)
            val dia = hoy.get(Calendar.DAY_OF_MONTH)

            val picker = DatePickerDialog(
                this,
                { _, y, m, d ->
                    // m es base 0
                    val fecha = "%02d/%02d/%04d".format(d, m + 1, y)
                    edtFechaNacimiento.setText(fecha)
                    fechaSeleccionada = fecha  // ← AQUÍ se actualiza la variable global
                },
                anio, mes, dia
            )


            // Sin fechas futuras
            picker.datePicker.maxDate = hoy.timeInMillis
            picker.show()
        }

        btnRegistrar.setOnClickListener {
            val nombre = edtNombre.text.toString().trim()
            val direccion = edtDireccion.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            // Validaciones
            if (nombre.isEmpty()) {
                edtNombre.error = "Ingrese su nombre"
                edtNombre.requestFocus()
                return@setOnClickListener
            }

            if (fechaSeleccionada.isEmpty()) {
                Toast.makeText(this, "Seleccione su fecha de nacimiento", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (direccion.isEmpty() || direccion.length < 5) {
                edtDireccion.error = "Ingrese una dirección válida (mín. 5 caracteres)"
                edtDireccion.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                edtEmail.error = "Ingrese un correo válido"
                edtEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || password.length < 6) {
                edtPassword.error = "La contraseña debe tener al menos 6 caracteres"
                edtPassword.requestFocus()
                return@setOnClickListener
            }

            // Crear objeto usuario
            val usuario = UsuarioEntity(
                nombre = nombre,
                fechaNacimiento = fechaSeleccionada,
                direccion = direccion,
                email = email,
                password = password
            )

            // Guardar en Room y Firebase
            val dao = AppDatabase.getInstance(this).usuarioDao()

            lifecycleScope.launch {
                try {
                    // Verificar si el email ya existe
                    val usuarioExistente = dao.buscarPorEmail(email)
                    if (usuarioExistente != null) {
                        runOnUiThread {
                            Toast.makeText(
                                this@RegisterActivity,
                                "El email ya está registrado",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        return@launch
                    }

                    // Guardar en Room
                    dao.insertar(usuario)

                    // Guardar en Firebase
                    FirebaseService.guardarUsuario(usuario)

                    runOnUiThread {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Usuario registrado exitosamente 🎉🍰",
                            Toast.LENGTH_SHORT
                        ).show()
                        limpiarCampos(edtNombre, edtDireccion, edtEmail, edtPassword)
                        finish()
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Error al registrar: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun limpiarCampos(vararg campos: EditText) {
        campos.forEach { it.setText("") }
        fechaSeleccionada = ""
    }
}