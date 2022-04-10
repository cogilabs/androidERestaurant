package fr.isen.girou.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Half.toFloat
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import fr.isen.girou.androiderestaurant.cart.Cart
import fr.isen.girou.androiderestaurant.cart.ShoppingCartActivity
import fr.isen.girou.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.girou.androiderestaurant.model.Item

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    lateinit var selectView: TextView
    lateinit var btnPlus: Button
    lateinit var btnMinus: Button
    var selected : Float = 1F
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getSerializableExtra(CategorieActivity.ITEM_KEY) as Item
        binding.titreDetail.text = item.name_fr
        binding.ingredientsText.text = item.ingredients.joinToString { it.name_fr }

        val carouselAdapter = CarouselAdapter(this, item.images)

        binding.detailSlider.adapter = carouselAdapter

        btnPlus = findViewById(R.id.btnPlus)
        btnMinus = findViewById(R.id.btnMinus)
        selectView = findViewById(R.id.selectView)
        selectView.text = selected.toString()
        doTotal(item, selected)

        btnPlus.setOnClickListener {
            if (selected >= 0){
                selected++
                selectView.text = selected.toString()
                doTotal(item, selected)
            }
        }
        btnMinus.setOnClickListener {
            if (selected > 0){
                selected--
                selectView.text = selected.toString()
                doTotal(item, selected)
            }
        }
        /*binding.btnTotal.setOnClickListener {
            item.let { item ->
                this.cacheDir.absolutePath
                val cart = Cart.getCart(this)
                cart.addItem(item, selected.toInt())
                cart.save(this)
                Snackbar.make(binding.btnTotal, R.string.itemAdded, Snackbar.LENGTH_LONG).show()
                invalidateOptionsMenu()
            }
        }*/
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
        Toast.makeText(this@DetailActivity, "panier", Toast.LENGTH_SHORT).show()
        true
    }

    private fun doTotal(item: Item, selected: Float) {
        val ttlPrix: String = item.prices[0].price
        val total: Float = ttlPrix.toFloat() * selected
        val totalString: String = "Total : " + total.toString() + "€"

        binding.btnTotal.text = totalString
    }
}