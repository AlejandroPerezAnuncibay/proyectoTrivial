
package com.alejandro.proyectoTrivial.anadirpregunta

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alejandro.proyectoTrivial.R
import com.google.android.material.textfield.TextInputEditText

const val PREGUNTA_TITULO = "titulo"
const val RESPUESTA_CORRECTA = "correcta"
const val RESPUESTA_FALSA1 = "falsa1"
const val RESPUESTA_FALSA2 = "falsa2"
const val RESPUESTA_FALSA3 = "falsa3"

class AnadirPreguntaActivity : AppCompatActivity() {
    private lateinit var anadirTituloPregunta: TextInputEditText
    private lateinit var anadirRespuestaCorrecta: TextInputEditText
    private lateinit var anadirRespuestaFalsa1: TextInputEditText
    private lateinit var anadirRespuestaFalsa2: TextInputEditText
    private lateinit var anadirRespuestaFalsa3: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.anadir_pelicula_layout)

        findViewById<Button>(R.id.done_button).setOnClickListener {
            anadirPregunta()
        }
        anadirTituloPregunta = findViewById(R.id.titulo_pregunta)
        anadirRespuestaCorrecta = findViewById(R.id.respuesta_correcta)
        anadirRespuestaFalsa1 = findViewById(R.id.respuesta_falsa_1)
        anadirRespuestaFalsa2 = findViewById(R.id.respuesta_falsa_2)
        anadirRespuestaFalsa3 = findViewById(R.id.respuesta_falsa_3)
    }

    private fun anadirPregunta() {
        val resultIntent = Intent()

        if (anadirTituloPregunta.text.isNullOrEmpty() || anadirRespuestaCorrecta.text.isNullOrEmpty() || anadirRespuestaFalsa1.text.isNullOrEmpty() || anadirRespuestaFalsa2.text.isNullOrEmpty() || anadirRespuestaFalsa3.text.isNullOrEmpty()) {
            Toast.makeText(applicationContext,"No se pueden dejar campos vacios",Toast.LENGTH_SHORT).show()

            setResult(RESULT_CANCELED, resultIntent)
        } else {
            val titulo = anadirTituloPregunta.text.toString()
            val correcta = anadirRespuestaCorrecta.text.toString()
            val falsa1 = anadirRespuestaFalsa1.text.toString()
            val falsa2 = anadirRespuestaFalsa2.text.toString()
            val falsa3 = anadirRespuestaFalsa3.text.toString()
            resultIntent.putExtra(PREGUNTA_TITULO, titulo)
            resultIntent.putExtra(RESPUESTA_CORRECTA, correcta)
            resultIntent.putExtra(RESPUESTA_FALSA1, falsa1)
            resultIntent.putExtra(RESPUESTA_FALSA2, falsa2)
            resultIntent.putExtra(RESPUESTA_FALSA3, falsa3)
            setResult(RESULT_OK, resultIntent)
        }
        finish()
    }
}