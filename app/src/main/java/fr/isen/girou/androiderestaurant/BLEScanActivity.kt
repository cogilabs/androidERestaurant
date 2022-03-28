package fr.isen.girou.androiderestaurant

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import fr.isen.girou.androiderestaurant.databinding.ActivityBlescanBinding

class BLEScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBlescanBinding
    private var scanning = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBlescanBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.scanBLEText.text = "Lancer le scan"
        binding.scanBLEBtn.setImageResource(R.drawable.ic_baseline_play_arrow_50)
        binding.scanBLEProgressBar.visibility = View.INVISIBLE
        binding.scanBLEProgressBar.isIndeterminate = true

            binding.scanBLEBtn.setOnClickListener {
                if (scanning) {
                    binding.scanBLEText.text = "Lancer le scan"
                    binding.scanBLEBtn.setImageResource(R.drawable.ic_baseline_play_arrow_50)
                    binding.scanBLEProgressBar.visibility = View.INVISIBLE
                } else {
                    binding.scanBLEText.text = "Scan en cours..."
                    binding.scanBLEBtn.setImageResource(R.drawable.ic_baseline_pause_50)
                    binding.scanBLEProgressBar.visibility = View.VISIBLE
                }
                scanning = !scanning
            }
    }
}