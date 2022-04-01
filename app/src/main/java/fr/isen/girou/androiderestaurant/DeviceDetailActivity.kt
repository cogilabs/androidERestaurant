package fr.isen.girou.androiderestaurant

import android.annotation.SuppressLint
import android.bluetooth.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.girou.androiderestaurant.databinding.ActivityBlescanBinding
import fr.isen.girou.androiderestaurant.databinding.ActivityDeviceDetailBinding

@SuppressLint("MissingPermission")
class DeviceDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeviceDetailBinding
    private var bluetoothGatt: BluetoothGatt? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDeviceDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val device = intent.getParcelableExtra<BluetoothDevice?>(BLEScanActivity.DEVICE_KEY)
        binding.deviceName.text = device?.name ?: "Nom inconnu"
        binding.deviceStatus.text = getString(R.string.ble_device_disconnected)

        connectToDevice(device)

    }

    private fun connectToDevice(device: BluetoothDevice?) {
        bluetoothGatt = device?.connectGatt(this, true, object: BluetoothGattCallback() {
            override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
                super.onConnectionStateChange(gatt, status, newState)
                connectionStateChange(gatt, newState)
            }

            override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
                super.onServicesDiscovered(gatt, status)
            }

            override fun onCharacteristicRead(
                gatt: BluetoothGatt?,
                characteristic: BluetoothGattCharacteristic?,
                status: Int
            ) {
                super.onCharacteristicRead(gatt, characteristic, status)
            }
        })
        bluetoothGatt?.connect()
    }

    private fun connectionStateChange(gatt: BluetoothGatt?, newState: Int) {
        val state = if(newState == BluetoothProfile.STATE_CONNECTED) {
            gatt?.discoverServices()
            getString(R.string.ble_device_connected)
        } else {
            getString(R.string.ble_device_disconnected)
        }
        runOnUiThread {
            binding.deviceStatus.text = state
        }
    }

    override fun onStop() {
        super.onStop()
        closeBluetoothGatt()
    }

    private fun closeBluetoothGatt() {
        bluetoothGatt?.close()
        bluetoothGatt = null
    }
}