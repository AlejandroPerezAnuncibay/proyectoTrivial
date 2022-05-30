
package com.alejandro.proyectoTrivial.listaPreguntas

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alejandro.proyectoTrivial.datos.DataSource
import com.alejandro.proyectoTrivial.datos.Pregunta
import kotlin.random.Random

class ListaPreguntasViewModel(val dataSource: DataSource) : ViewModel() {

    val preguntasLiveData = dataSource.getPreguntasList()

    fun insertarPregunta(titulo: String?, correcta: String?, falsa1: String?, falsa2: String?, falsa3: String?) {
        if (titulo == null || correcta == null || falsa1 == null || falsa2 == null || falsa3 == null) {
            return
        }

        val nuevaPregunta = Pregunta(
            Random.nextLong(),
            titulo,
            correcta,
            falsa1,
            falsa2,
            falsa3
        )

        dataSource.anadirPregunta(nuevaPregunta)
    }
}

class ListaDePreguntasViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListaPreguntasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListaPreguntasViewModel(
                dataSource = DataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}