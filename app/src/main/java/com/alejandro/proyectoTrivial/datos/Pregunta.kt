
package com.alejandro.proyectoTrivial.datos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "preguntas")
data class Pregunta(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name= "titulo") var titulo: String,
    @ColumnInfo(name = "correcta") var correcta: String,
    @ColumnInfo(name = "falsa1") var falsa1: String,
    @ColumnInfo(name = "falsa2") var falsa2: String,
    @ColumnInfo(name = "falsa3") var falsa3: String,
)