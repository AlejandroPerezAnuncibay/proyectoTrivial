

package com.alejandro.proyectoTrivial.listaPreguntas

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alejandro.proyectoTrivial.preguntaDetail.PreguntaDetailActivity
import com.alejandro.proyectoTrivial.R
import com.alejandro.proyectoTrivial.anadirpregunta.*
import com.alejandro.proyectoTrivial.datos.AppDatabase
import com.alejandro.proyectoTrivial.datos.DataSourceViewModel
import com.alejandro.proyectoTrivial.datos.Pregunta
import com.alejandro.proyectoTrivial.datos.Repositorio
import com.alejandro.proyectoTrivial.game.GameActivity
import com.google.android.material.navigation.NavigationView

const val PREGUNTA_ID = "pregunta id"

class ListaPreguntasActivity : AppCompatActivity() {
    private val preguntaNuevaActivityRequestCode = 1
    lateinit var toggle: ActionBarDrawerToggle
    private val database by lazy { AppDatabase.getDatabase(this) }
    val miRepositorio by lazy { Repositorio(database.miDAO()) }

    private val miViewModel:DataSourceViewModel by viewModels{DataSourceViewModel.DataSourceViewModelFactory(miRepositorio)}




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val headerAdapter = HeaderAdapter()
        val preguntasAdapter = PreguntasAdapter { pregunta -> adapterOnClick(pregunta) }
        val concatAdapter = ConcatAdapter(headerAdapter, preguntasAdapter)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = concatAdapter

        (this).miViewModel.allPreguntas.observe(this){
                Pregunta -> Pregunta?.let {
            preguntasAdapter.submitList(it as MutableList<Pregunta>)
            headerAdapter.updateContadorPreguntas(it.size)
        }
        }
        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view_id)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener{
            when(it.itemId){
                R.id.nav_anadir -> fabOnClick()
                R.id.nav_play -> playOnClick()

            }
            true
        }



    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return false
    }



    private fun adapterOnClick(pregunta: Pregunta) {
        val intent = Intent(this, PreguntaDetailActivity()::class.java)
        intent.putExtra(PREGUNTA_ID, pregunta.id)
        startActivity(intent)
    }

    private fun fabOnClick() {
        val intent = Intent(this, AnadirPreguntaActivity::class.java)
        startActivityForResult(intent, preguntaNuevaActivityRequestCode)
    }

    private fun playOnClick(){
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == preguntaNuevaActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val titulo = data.getStringExtra(PREGUNTA_TITULO)
                val correcta = data.getStringExtra(RESPUESTA_CORRECTA)
                val falsa1 = data.getStringExtra(RESPUESTA_FALSA1)
                val falsa2 = data.getStringExtra(RESPUESTA_FALSA2)
                val falsa3 = data.getStringExtra(RESPUESTA_FALSA3)
                if (titulo == null || correcta == null || falsa1 == null || falsa2 == null || falsa3 == null) {
                    return
                }
                val nuevaPregunta = Pregunta(
                    0,
                    titulo,
                    correcta,
                    falsa1,
                    falsa2,
                    falsa3
                )
                miViewModel.anadirPregunta(nuevaPregunta)
            }
        }
    }
}