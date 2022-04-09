@file:Suppress("UNUSED_PARAMETER")

package fr.isen.girou.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.isen.girou.androiderestaurant.ble.BLEScanActivity
import fr.isen.girou.androiderestaurant.cart.ShoppingCartActivity
import fr.isen.girou.androiderestaurant.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.btnEntree.setOnClickListener {
            goToCategory(getString(R.string.entrees))
        }

        binding.btnPlats.setOnClickListener {
            goToCategory(getString(R.string.plats))
        }

        binding.btnDesserts.setOnClickListener {
            goToCategory(getString(R.string.desserts))
        }

        binding.btnBluetooth.setOnClickListener {
            val intent = Intent(this, BLEScanActivity::class.java)
            startActivity(intent)
        }

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.panier -> {
                panier()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun panier() {
        val intent = Intent(this, ShoppingCartActivity::class.java)
        startActivity(intent)
        Toast.makeText(this@HomeActivity, "panier", Toast.LENGTH_SHORT).show()
        true
    }
    private fun goToCategory(categorie: String) {
        val intent = Intent(this, CategorieActivity::class.java)
        intent.putExtra("categorie", categorie)
        startActivity(intent)
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
        Log.d(tag, "Sortie de la page d'acceuil")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "Page d'acceuil détruite")
    }
}