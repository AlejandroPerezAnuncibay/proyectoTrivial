

package com.alejandro.proyectoTrivial.listaPreguntas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alejandro.proyectoTrivial.R
import com.alejandro.proyectoTrivial.datos.Pregunta

class PreguntasAdapter(private val onClick: (Pregunta) -> Unit) :
    ListAdapter<Pregunta, PreguntasAdapter.PreguntasViewHolder>(PreguntaDiffCallback) {

    class PreguntasViewHolder(itemView: View, val onClick: (Pregunta) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private val preguntaTitulo: TextView = itemView.findViewById(R.id.pregunta_titulo)
        private var currentPregunta: Pregunta? = null

        init {
            itemView.setOnClickListener {
                currentPregunta?.let {
                    onClick(it)
                }
            }
        }

        fun bind(pregunta: Pregunta) {
            currentPregunta = pregunta

            preguntaTitulo.text = pregunta.titulo
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreguntasViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pelicula_item, parent, false)
        return PreguntasViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: PreguntasViewHolder, position: Int) {
        val pregunta = getItem(position)
        holder.bind(pregunta)

    }
}

object PreguntaDiffCallback : DiffUtil.ItemCallback<Pregunta>() {
    override fun areItemsTheSame(oldItem: Pregunta, newItem: Pregunta): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Pregunta, newItem: Pregunta): Boolean {
        return oldItem.id == newItem.id
    }
}