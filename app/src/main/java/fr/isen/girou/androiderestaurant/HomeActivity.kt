package fr.isen.girou.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.isen.girou.androiderestaurant.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnEntree.setOnClickListener {
            val intent = Intent(this, EntreesActivity::class.java)
            // start your next activity
            startActivity(intent)
        }

        binding.btnPlats.setOnClickListener {
            val intent = Intent(this, PlatsActivity::class.java)
            // start your next activity
            startActivity(intent)
        }

        binding.btnDesserts.setOnClickListener {
            val intent = Intent(this, DessertsActivity::class.java)
            // start your next activity
            startActivity(intent)
        }

    }

    private fun toastMsg(msg: String?) {
        val toast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
        toast.show()
    }

    fun displayEntreesMsg(view: View) {
        toastMsg("Entrées")
    }

    fun displayPlatsMsg(view: View) {
        toastMsg("Plats")
    }

    fun displayDessertsMsg(view: View) {
        toastMsg("Desserts")
    }

    private val tag = "LogHomeActivity"

    override fun onStop() {
        super.onStop()
        Log.i(tag, "Sortie de la page d'acceuil")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(tag, "Page d'acceuil détruite")
    }
}