package fr.isen.girou.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class PlatsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plats)
    }

    private val tag = "LogPlatsActivity"

    override fun onStop() {
        super.onStop()
        Log.i(tag, "Sortie de la page des plats")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(tag, "Page des plats détruite")
    }
}