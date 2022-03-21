package fr.isen.girou.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class DessertsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desserts)
    }

    private val tag = "LogDessertsActivity"

    override fun onStop() {
        super.onStop()
        Log.i(tag, "Sortie de la page des desserts")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(tag, "Page des desserts détruite")
    }
}