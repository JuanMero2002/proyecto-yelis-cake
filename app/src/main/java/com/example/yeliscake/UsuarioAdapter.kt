package com.example.yeliscake

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.yeliscake.model.UsuarioEntity
import java.text.SimpleDateFormat
import java.util.*

class UsuarioAdapter : ListAdapter<UsuarioEntity, UsuarioAdapter.VH>(Diff()) {

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNombre: TextView = itemView.findViewById(R.id.txtItemNombre)
        val txtEmail: TextView = itemView.findViewById(R.id.txtItemEmail)
        val txtDetalle: TextView = itemView.findViewById(R.id.txtItemDetalle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_usuario, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val usuario = getItem(position)
        holder.txtNombre.text = usuario.nombre
        holder.txtEmail.text = usuario.email
        holder.txtDetalle.text = buildString {
            append("Fecha Nac.: ${usuario.fechaNacimiento}\n")
            append("Dirección: ${usuario.direccion}\n")
            append("Registrado: ${formatearFecha(usuario.fechaRegistro)}")
        }
    }

    private fun formatearFecha(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    private class Diff : DiffUtil.ItemCallback<UsuarioEntity>() {
        override fun areItemsTheSame(oldItem: UsuarioEntity, newItem: UsuarioEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UsuarioEntity, newItem: UsuarioEntity) =
            oldItem == newItem
    }
}