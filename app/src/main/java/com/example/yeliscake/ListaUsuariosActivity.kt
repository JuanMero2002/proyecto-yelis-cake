package com.example.yeliscake

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yeliscake.cloud.FirebaseService
import com.example.yeliscake.database.AppDatabase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ListaUsuariosActivity : AppCompatActivity() {

    private lateinit var rvUsuarios: RecyclerView
    private lateinit var adapter: UsuarioAdapter
    private lateinit var btnRoom: Button
    private lateinit var btnFirebase: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_usuarios)

        // Inicializar vistas
        rvUsuarios = findViewById(R.id.rvUsuarios)
        btnRoom = findViewById(R.id.btnRoom)
        btnFirebase = findViewById(R.id.btnFirebase)

        // Configurar RecyclerView
        adapter = UsuarioAdapter()
        rvUsuarios.layoutManager = LinearLayoutManager(this)
        rvUsuarios.adapter = adapter

        val dao = AppDatabase.getInstance(this).UsuarioDao()

        // Botón para ver datos de Room
        btnRoom.setOnClickListener {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    dao.obtenerTodos().collectLatest { listaUsuarios ->
                        adapter.submitList(listaUsuarios)
                    }
                }
            }
        }

        // Botón para ver datos de Firebase
        btnFirebase.setOnClickListener {
            FirebaseService.obtenerUsuarios { listaFirestore ->
                adapter.submitList(listaFirestore)
            }
        }
    }