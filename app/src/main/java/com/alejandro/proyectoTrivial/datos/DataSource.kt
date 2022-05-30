package com.alejandro.proyectoTrivial.datos

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/*TODO: Implementar el VIEWMODEL punto nº6 del documento*/
class DataSource(resources: Resources) {
    private val listaInicialPreguntas = listaPeliculas(resources)
    private val preguntasLiveData = MutableLiveData(listaInicialPreguntas)

    fun anadirPregunta(pregunta: Pregunta) {
        val currentList = preguntasLiveData.value
        if (currentList == null) {
            preguntasLiveData.postValue(listOf(pregunta))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, pregunta)
            preguntasLiveData.postValue(updatedList)
        }
    }

    fun eliminarPregunta(pelicula: Pregunta) {
        val currentList = preguntasLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(pelicula)
            preguntasLiveData.postValue(updatedList)
        }
    }
    fun actualizarPregunta(pregunta: Pregunta){

        val currentList = preguntasLiveData.value
        if(currentList != null){
            val updatedList = currentList.toMutableList()
            var contador = 0
            var encontrado = false
            updatedList.forEach {
                if(it.id == pregunta.id){
                    encontrado = true
                }
                if(encontrado){
                    return@forEach
                }else{
                    contador += 1
                }
            }
            Log.d("a",contador.toString())
            if(contador < updatedList.size){
                updatedList[contador] = pregunta
                preguntasLiveData.postValue(updatedList)
            }

        }

    }
    fun getPreguntaForId(id: Long): Pregunta? {
        preguntasLiveData.value?.let { pregunta ->
            return pregunta.firstOrNull{ it.id == id}
        }
        return null
    }

    fun getPreguntasList(): LiveData<List<Pregunta>> {
        return preguntasLiveData
    }

    companion object {
        private var INSTANCE: DataSource? = null

        fun getDataSource(resources: Resources): DataSource {
            return synchronized(DataSource::class) {
                val newInstance = INSTANCE ?: DataSource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}