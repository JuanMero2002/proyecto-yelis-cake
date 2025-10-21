package com.example.yeliscake.cloud

import android.util.Log
import com.example.yeliscake.model.UsuarioEntity
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseService {
    private val db = FirebaseFirestore.getInstance()
    private const val COLLECTION_USUARIOS = "usuarios"

    fun guardarUsuario(usuario: UsuarioEntity) {
        val data = hashMapOf(
            "nombre" to usuario.nombre,
            "fechaNacimiento" to usuario.fechaNacimiento,
            "direccion" to usuario.direccion,
            "email" to usuario.email,
            "fechaRegistro" to usuario.fechaRegistro
        )

        db.collection(COLLECTION_USUARIOS)
            .add(data)
            .addOnSuccessListener {
                Log.d("FirebaseService", "Usuario guardado en Firestore: ${usuario.email}")
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseService", "Error al guardar usuario", e)
            }
    }

    fun obtenerUsuarios(callback: (List<UsuarioEntity>) -> Unit) {
        db.collection(COLLECTION_USUARIOS)
            .get()
            .addOnSuccessListener { result ->
                val lista = result.map { doc ->
                    UsuarioEntity(
                        id = 0, // Firebase no guarda el ID local
                        nombre = doc.getString("nombre") ?: "",
                        fechaNacimiento = doc.getString("fechaNacimiento") ?: "",
                        direccion = doc.getString("direccion") ?: "",
                        email = doc.getString("email") ?: "",
                        password = "", // No recuperamos la contraseña por seguridad
                        fechaRegistro = doc.getLong("fechaRegistro") ?: 0L
                    )
                }
                callback(lista)
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseService", "Error al obtener usuarios", e)
                callback(emptyList())
            }
    }

    fun buscarPorEmail(email: String, callback: (UsuarioEntity?) -> Unit) {
        db.collection(COLLECTION_USUARIOS)
            .whereEqualTo("email", email)
            .limit(1)
            .get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    val doc = result.documents[0]
                    val usuario = UsuarioEntity(
                        id = 0,
                        nombre = doc.getString("nombre") ?: "",
                        fechaNacimiento = doc.getString("fechaNacimiento") ?: "",
                        direccion = doc.getString("direccion") ?: "",
                        email = doc.getString("email") ?: "",
                        password = "",
                        fechaRegistro = doc.getLong("fechaRegistro") ?: 0L
                    )
                    callback(usuario)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener { e ->
                Log.e("FirebaseService", "Error al buscar usuario", e)
                callback(null)
            }
    }
}