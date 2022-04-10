package fr.isen.girou.androiderestaurant.cart

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.girou.androiderestaurant.HomeActivity
import fr.isen.girou.androiderestaurant.R
import fr.isen.girou.androiderestaurant.basket.ShoppingCartAdapter
import fr.isen.girou.androiderestaurant.databinding.ActivityShoppingCartBinding
import org.json.JSONObject
import java.io.File

class ShoppingCartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShoppingCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShoppingCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //loadList()

    }

    private fun loadList() {
        val cart = Cart.getCart(this)
        val items = cart.items
        binding.shoppingCartRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.shoppingCartRecyclerView.adapter = ShoppingCartAdapter(items) {
            cart.removeItem(it)
            cart.save(this)
            loadList()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Toast.makeText(this, "SEND ORDER", Toast.LENGTH_LONG).show()
        }
    }

    companion object{
        const val REQUEST_CODE = 111
    }
}