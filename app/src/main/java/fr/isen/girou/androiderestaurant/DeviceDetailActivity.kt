package fr.isen.girou.androiderestaurant

import android.bluetooth.BluetoothDevice
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.girou.androiderestaurant.databinding.ActivityBlescanBinding
import fr.isen.girou.androiderestaurant.databinding.ActivityDeviceDetailBinding

class DeviceDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeviceDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDeviceDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val device = intent.getParcelableExtra<BluetoothDevice?>(BLEScanActivity.DEVICE_KEY)


    }
}