
package com.alejandro.proyectoTrivial.listaPreguntas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alejandro.proyectoTrivial.R


class HeaderAdapter: RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder>() {
    private var preguntasCount: Int = 0

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val cabeceraNumeroTextView: TextView = itemView.findViewById(R.id.header)

        fun bind(preguntasCount: Int) {
            cabeceraNumeroTextView.text = preguntasCount.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.header_item, parent, false)
        return HeaderViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.bind(preguntasCount)
    }

    override fun getItemCount(): Int {
        return 1
    }

    fun updateContadorPreguntas(actualizadoContador: Int) {
        preguntasCount = actualizadoContador
        notifyDataSetChanged()
    }
}