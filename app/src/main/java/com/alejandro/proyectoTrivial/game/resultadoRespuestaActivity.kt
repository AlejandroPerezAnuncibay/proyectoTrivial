
package com.alejandro.proyectoTrivial.game

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alejandro.proyectoTrivial.R
import com.alejandro.proyectoTrivial.listaPreguntas.ListaPreguntasActivity

class resultadoRespuestaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.resultado_respuesta)

        var textoTitulo = ""
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            textoTitulo = bundle.getString(TEXTO_RESULTADO).toString()
        }
        var vv: View = findViewById(R.id.background)
        if(textoTitulo.equals("Respuesta Correcta")){
            vv.setBackgroundColor(Color.GREEN)
        }else{
            vv.setBackgroundColor(Color.RED)
        }

        findViewById<TextView>(R.id.resultado_texto).text = textoTitulo

        findViewById<Button>(R.id.resultado_btn_salir).setOnClickListener {
            salirAListadoDePreguntas()
        }
        findViewById<Button>(R.id.resultado_btn_siguiente).setOnClickListener {
            siguientePregunta()
        }
    }

    private fun salirAListadoDePreguntas(){
        val intent = Intent(this, ListaPreguntasActivity()::class.java)
        startActivity(intent)
    }

    private fun siguientePregunta(){
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }
}