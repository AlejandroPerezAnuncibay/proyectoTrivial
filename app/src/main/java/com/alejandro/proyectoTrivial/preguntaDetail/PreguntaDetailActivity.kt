
package com.alejandro.proyectoTrivial.preguntaDetail

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.alejandro.proyectoTrivial.R
import com.alejandro.proyectoTrivial.datos.AppDatabase
import com.alejandro.proyectoTrivial.datos.DataSourceViewModel
import com.alejandro.proyectoTrivial.datos.Pregunta
import com.alejandro.proyectoTrivial.datos.Repositorio
import com.alejandro.proyectoTrivial.listaPreguntas.PREGUNTA_ID


class PreguntaDetailActivity : AppCompatActivity() {

    private val database by lazy { AppDatabase.getDatabase(this) }
    val miRepositorio by lazy { Repositorio(database.miDAO()) }

    private val miViewModel: DataSourceViewModel by viewModels{ DataSourceViewModel.DataSourceViewModelFactory(miRepositorio)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pregunta_detail_activity)

        var currentPreguntaId: Long? = null

        val tituloPregunta: TextView = findViewById(R.id.pregunta_detail_titulo)
        val respuestaCorrecta: TextView = findViewById(R.id.pregunta_detail_correcta)
        val respuestaFalsa1: TextView = findViewById(R.id.pregunta_detail_falsa1)
        val respuestaFalsa2: TextView = findViewById(R.id.pregunta_detail_falsa2)
        val respuestaFalsa3: TextView = findViewById(R.id.pregunta_detail_falsa3)

        val removePreguntaBoton: Button = findViewById(R.id.remove_button)
        val updatePreguntaBoton: Button = findViewById(R.id.update_button)
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            currentPreguntaId = bundle.getLong(PREGUNTA_ID)
        }

        currentPreguntaId?.let {
            var currentPregunta = Pregunta(-1,"","","","","")
                miViewModel.getPreguntaById(it).asLiveData().observe(this){
                pregunta -> pregunta?.let {
                    if (it.size > 0){
                        currentPregunta = it.get(0)
                        tituloPregunta.text = currentPregunta?.titulo
                        respuestaCorrecta.text = currentPregunta?.correcta
                        respuestaFalsa1.text = currentPregunta?.falsa1
                        respuestaFalsa2.text = currentPregunta?.falsa2
                        respuestaFalsa3.text = currentPregunta?.falsa3
                    }else{
                        cerrarPantallaError()
                    }
                }
            }

            updatePreguntaBoton.setOnClickListener {
                if (currentPregunta != null && currentPregunta.id != (-1).toLong()){
                    val titulo = findViewById(R.id.pregunta_detail_titulo) as EditText
                    val correcta = findViewById(R.id.pregunta_detail_correcta) as EditText
                    val falsa1 = findViewById(R.id.pregunta_detail_falsa1) as EditText
                    val falsa2 = findViewById(R.id.pregunta_detail_falsa2) as EditText
                    val falsa3 = findViewById(R.id.pregunta_detail_falsa3) as EditText
                    currentPregunta.titulo = titulo.text.toString()
                    currentPregunta.correcta = correcta.text.toString()
                    currentPregunta.falsa1 = falsa1.text.toString()
                    currentPregunta.falsa2 = falsa2.text.toString()
                    currentPregunta.falsa3 = falsa3.text.toString()
                    miViewModel.actualizarPregunta(currentPregunta)
                }else{
                    cerrarPantallaError()
                }
                finish()
            }
            removePreguntaBoton.setOnClickListener {
                if (currentPregunta != null) {
                    miViewModel.eliminarPregunta(currentPregunta)
                }
                finish()
            }
        }

    }

    fun cerrarPantallaError(){
        val dialog = AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage("Ha ocurrido un error al intentar abrir la pregunta")
            .setNegativeButton("Volver") { view, _ ->
                Toast.makeText(this, "Cancel button pressed", Toast.LENGTH_SHORT).show()
                view.dismiss()
                finish()
            }
            .setCancelable(false)
            .create()

        dialog.show()
    }
}