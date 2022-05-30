package com.alejandro.proyectoTrivial.datos

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class Repositorio (val miDAO: PreguntasDao){
    val listaPreguntas: Flow<List<Pregunta>> = miDAO.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(pregunta: Pregunta){
        miDAO.insert(pregunta)
    }

    fun findAll(): Flow<List<Pregunta>>{
        return miDAO.getAll()
    }

    fun findByTitulo(name: String): Flow<List<Pregunta>>{
        return miDAO.findByTitulo(name)
    }

    fun findById(id: Long): Flow<List<Pregunta>>{
        return miDAO.findById(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(miPregunta: Pregunta){
        miDAO.delete(miPregunta)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(miPregunta: Pregunta){
        miDAO.update(miPregunta)
    }

}