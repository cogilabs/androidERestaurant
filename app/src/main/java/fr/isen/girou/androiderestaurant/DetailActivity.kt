package fr.isen.girou.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.girou.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.girou.androiderestaurant.model.Item

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getSerializableExtra(CategorieActivity.ITEM_KEY) as Item
        binding.titreDetail.text = item.name_fr
        binding.ingredientsText.text = item.ingredients.joinToString { it.name_fr }

        val carouselAdapter = CarouselAdapter(this, item.images)

        binding.detailSlider.adapter = carouselAdapter
    }
}