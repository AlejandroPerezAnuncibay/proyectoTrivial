package com.alejandro.proyectoTrivial.datos

import androidx.lifecycle.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class DataSourceViewModel(private val miRepositorio: Repositorio): ViewModel() {

    val allPreguntas: LiveData<List<Pregunta>> = miRepositorio.listaPreguntas.asLiveData()
    lateinit var miPregunta:LiveData<Pregunta>

    fun anadirPregunta(miPregunta: Pregunta) = viewModelScope.launch {
        miRepositorio.insert(miPregunta)
    }

    fun eliminarPregunta(miPregunta: Pregunta) = viewModelScope.launch {
        miRepositorio.delete(miPregunta)
    }
    fun actualizarPregunta(miPregunta: Pregunta) = viewModelScope.launch {
        miRepositorio.update(miPregunta)
    }

    fun getPreguntaById(id: Long): Flow<List<Pregunta>> {
        return miRepositorio.findById(id)
    }

    fun getPreguntaByTitulo(titulo: String): Flow<List<Pregunta>> {
        return miRepositorio.findByTitulo(titulo)
    }

    fun getPreguntasList(): Flow<List<Pregunta>> {
        return miRepositorio.findAll()
    }

    class DataSourceViewModelFactory(private val repositorio: Repositorio) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(DataSourceViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return DataSourceViewModel(repositorio) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}