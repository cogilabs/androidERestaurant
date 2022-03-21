package fr.isen.girou.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.isen.girou.androiderestaurant.databinding.ActivityHomeBinding
import java.util.logging.Logger


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.button.setOnClickListener {
            val intent = Intent(this, EntreesActivity::class.java)
            // start your next activity
            startActivity(intent)
        }

        binding.button3.setOnClickListener {
            val intent = Intent(this, PlatsActivity::class.java)
            // start your next activity
            startActivity(intent)
        }

        binding.button4.setOnClickListener {
            val intent = Intent(this, DessertsActivity::class.java)
            // start your next activity
            startActivity(intent)
        }

        override fun onDestroy(savedInstanceState: Bundle?) {
            super.onDestroy()
            val log = Logger.getLogger(HomeActivity::class.java.name)
            log.warning("Hello World")
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

}