package fr.isen.girou.androiderestaurant.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import fr.isen.girou.androiderestaurant.HomeActivity
import fr.isen.girou.androiderestaurant.R
import fr.isen.girou.androiderestaurant.databinding.ActivityShoppingCartBinding
import java.io.File

class ShoppingCartActivity : AppCompatActivity() {
    private lateinit var monRecycler: RecyclerView
    private lateinit var binding: ActivityShoppingCartBinding
    private lateinit var cartList: ShoppingCartList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShoppingCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val file = File(cacheDir.absolutePath + "cart.json")

        cartList = if(file.exists()) {
            Gson().fromJson( file.readText(), ShoppingCartList::class.java )
        } else {
            ShoppingCartList(arrayListOf())
        }


        monRecycler = binding.shoppingCartRecyclerView
        monRecycler.layoutManager = LinearLayoutManager(this)
        /*monRecycler.adapter = ShoppingCartList(cartList.cart){
            deleteItem2(it)
        }*/

        binding.btnCheckout.setOnClickListener{
            commander()
        }

    }

    private fun commander(){
        cartList = ShoppingCartList(arrayListOf())
        val strCart = Gson().toJson(cartList, ShoppingCartList::class.java)
        File(cacheDir.absolutePath + "cart.json").writeText(strCart )


        val intent = Intent(this, HomeActivity::class.java)
        Toast.makeText(applicationContext, R.string.toast, Toast.LENGTH_SHORT).show()
        startActivity(intent)

    }

}