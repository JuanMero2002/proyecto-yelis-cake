package com.example.yeliscake.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String,
    val fechaNacimiento: String,
    val direccion: String,
    val email: String,
    val password: String,
    val fechaRegistro: Long = System.currentTimeMillis()
)