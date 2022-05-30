
package com.alejandro.proyectoTrivial.game

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.alejandro.proyectoTrivial.R
import com.alejandro.proyectoTrivial.datos.AppDatabase
import com.alejandro.proyectoTrivial.datos.DataSourceViewModel
import com.alejandro.proyectoTrivial.datos.Pregunta
import com.alejandro.proyectoTrivial.datos.Repositorio
import kotlin.random.Random


const val TEXTO_RESULTADO = "texto_resultado"

class GameActivity : AppCompatActivity() {
    private val database by lazy { AppDatabase.getDatabase(this) }
    val miRepositorio by lazy { Repositorio(database.miDAO()) }
    private val miViewModel: DataSourceViewModel by viewModels{ DataSourceViewModel.DataSourceViewModelFactory(miRepositorio)}

    val fa = this

    var respuestaCorrectaIndice: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity)
        var preguntaSeleccionada = Pregunta(-1,"","","","","")

        (this).miViewModel.allPreguntas.observe(this){
                Pregunta -> Pregunta?.let {
            val countPreguntas = it.size
            if (countPreguntas > 0){
                val indexPreguntaSeleccionada = Random.nextInt(countPreguntas - 0)
                preguntaSeleccionada = it[indexPreguntaSeleccionada]
            }else{
                cerrarPantallaError("No hay preguntas para seleccionar")
            }

            if(preguntaSeleccionada.id != (-1).toLong()){
                findViewById<TextView>(R.id.pregunta).text = preguntaSeleccionada.titulo
                desordenarRespuestas(preguntaSeleccionada)
            }else{
                cerrarPantallaError("Ha ocurrido un error al seleccionar la pregunta")
            }

        }
        }

        findViewById<Button>(R.id.game_opcion1).setOnClickListener {
            eventoClickRespuesta(0)
        }
        findViewById<Button>(R.id.game_opcion2).setOnClickListener {
            eventoClickRespuesta(1)
        }
        findViewById<Button>(R.id.game_opcion3).setOnClickListener {
            eventoClickRespuesta(2)
        }
        findViewById<Button>(R.id.game_opcion4).setOnClickListener {
            eventoClickRespuesta(3)
        }

    }

    private fun desordenarRespuestas(preguntaSeleccionada: Pregunta){
        val indexCorrecta = Random.nextInt(4 - 0)
        if(indexCorrecta == 0){
            respuestaCorrectaIndice = 0;
            findViewById<Button>(R.id.game_opcion1).text = preguntaSeleccionada.correcta
            findViewById<Button>(R.id.game_opcion2).text = preguntaSeleccionada.falsa2
            findViewById<Button>(R.id.game_opcion3).text = preguntaSeleccionada.falsa3
            findViewById<Button>(R.id.game_opcion4).text = preguntaSeleccionada.falsa1
        }else if(indexCorrecta == 1){
            respuestaCorrectaIndice = 1;
            findViewById<Button>(R.id.game_opcion1).text = preguntaSeleccionada.falsa2
            findViewById<Button>(R.id.game_opcion2).text = preguntaSeleccionada.correcta
            findViewById<Button>(R.id.game_opcion3).text = preguntaSeleccionada.falsa3
            findViewById<Button>(R.id.game_opcion4).text = preguntaSeleccionada.falsa1
        }else if(indexCorrecta == 2){
            respuestaCorrectaIndice = 2;
            findViewById<Button>(R.id.game_opcion1).text = preguntaSeleccionada.falsa3
            findViewById<Button>(R.id.game_opcion2).text = preguntaSeleccionada.falsa2
            findViewById<Button>(R.id.game_opcion3).text = preguntaSeleccionada.correcta
            findViewById<Button>(R.id.game_opcion4).text = preguntaSeleccionada.falsa1
        }else{
            respuestaCorrectaIndice = 3;
            findViewById<Button>(R.id.game_opcion1).text = preguntaSeleccionada.falsa1
            findViewById<Button>(R.id.game_opcion2).text = preguntaSeleccionada.falsa2
            findViewById<Button>(R.id.game_opcion3).text = preguntaSeleccionada.falsa3
            findViewById<Button>(R.id.game_opcion4).text = preguntaSeleccionada.correcta
        }
    }

    private fun eventoClickRespuesta(opcionClickada: Int){
        if(opcionClickada == respuestaCorrectaIndice){
            val intent = Intent(this, resultadoRespuestaActivity()::class.java)
            intent.putExtra(TEXTO_RESULTADO, "Respuesta Correcta")
            startActivity(intent)
        }else{
            val intent = Intent(this, resultadoRespuestaActivity()::class.java)
            intent.putExtra(TEXTO_RESULTADO, "Respuesta Incorrecta")
            startActivity(intent)
        }
    }

    fun cerrarPantallaError(mensaje: String){
        val dialog = AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(mensaje)
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