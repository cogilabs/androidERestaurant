package fr.isen.girou.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class EntreesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrees)
    }

    private val tag = "LogEntreesActivity"

    override fun onStop() {
        super.onStop()
        Log.i(tag, "Sortie de la page des entrées")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(tag, "Page des entrées détruite")
    }
}