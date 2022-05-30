package com.alejandro.proyectoTrivial.datos

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PreguntasDao{
    @Query("select * from preguntas")
    fun getAll(): Flow<List<Pregunta>>

    @Query("select * from preguntas where titulo like :titulo")
    fun findByTitulo(titulo: String): Flow<List<Pregunta>>

    @Query("select * from preguntas where id = :id")
    fun findById(id: Long): Flow<List<Pregunta>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pregunta: Pregunta)

    @Delete
    suspend fun delete(pregunta: Pregunta)

    @Update
    suspend fun update(pregunta: Pregunta)
}