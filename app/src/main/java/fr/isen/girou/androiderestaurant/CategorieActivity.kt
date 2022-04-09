package fr.isen.girou.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.girou.androiderestaurant.databinding.ActivityCategorieBinding
import fr.isen.girou.androiderestaurant.model.DataResult
import org.json.JSONObject

class CategorieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategorieBinding
    private lateinit var items: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategorieBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        items = arrayListOf()

        val nomCategorie = intent.getStringExtra("categorie")
        binding.titreCategorie.text = nomCategorie

        val recyclerView: RecyclerView = binding.listeCategories
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = CategorieAdapter(arrayListOf()) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(ITEM_KEY, it)
            startActivity(intent)
        }
        getDataFromApi(intent.getStringExtra("categorie") ?: "")
        Log.d("LogApi",items.toString())
    }

    private fun getDataFromApi(category: String) {
        Volley.newRequestQueue(this)
        val url = "http://test.api.catering.bluecodegames.com/menu"
        val json = JSONObject()
        json.put("id_shop", "1")
        json.toString()
        val request = JsonObjectRequest(
            Request.Method.POST,url,json,
            { response ->
                val strResp = response.toString()
                val data = Gson().fromJson(strResp, DataResult::class.java)
                val items = data.data.firstOrNull { it.name_fr == category }?.items ?: arrayListOf()
                binding.listeCategories.adapter = CategorieAdapter(items) {
                    val intent = Intent(this, DetailActivity::class.java)
                    intent.putExtra(ITEM_KEY, it)
                    startActivity(intent)
                }

            }, {
                Log.e(tag, "Log Volley error: $it")
            })

        VolleySingleton.getInstance(this).addToRequestQueue(request)
    }

    companion object {
        const val ITEM_KEY = "item"
    }

    private val tag = "LogCategorieActivity"

    override fun onStop() {
        super.onStop()
        Log.d(tag, "Sortie de la page des catégories")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "Page des catégories détruite")
    }
}
