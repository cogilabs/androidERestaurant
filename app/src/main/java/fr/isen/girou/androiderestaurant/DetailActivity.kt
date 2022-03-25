package fr.isen.girou.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Half.toFloat
import android.view.View
import android.widget.Button
import android.widget.TextView
import fr.isen.girou.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.girou.androiderestaurant.model.Item

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    lateinit var selectView: TextView
    lateinit var btnPlus: Button
    lateinit var btnMinus: Button
    var selected : Float = 0F
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
    }

    private fun doTotal(item: Item, selected: Float) {
        val ttlPrix: String = item.prices[0].price
        val total: Float = ttlPrix.toFloat() * selected
        val totalString: String = "Total : " + total.toString() + "€"

        binding.btnTotal.text = totalString
    }
}