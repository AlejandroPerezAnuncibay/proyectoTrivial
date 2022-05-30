

package com.alejandro.proyectoTrivial.preguntaDetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alejandro.proyectoTrivial.datos.DataSource
import com.alejandro.proyectoTrivial.datos.Pregunta

class PreguntaDetailViewModel(private val datasource: DataSource) : ViewModel() {

    fun getPreguntaById(id: Long) : Pregunta? {
        return datasource.getPreguntaForId(id)
    }

    fun actualizarPregunta(pregunta: Pregunta) {
        datasource.actualizarPregunta(pregunta)
    }
    fun eliminarPregunta(pregunta: Pregunta) {
        datasource.eliminarPregunta(pregunta)
    }
}

class PreguntaDetailViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PreguntaDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PreguntaDetailViewModel(
                datasource = DataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}