package fr.isen.girou.androiderestaurant.ble

import android.annotation.SuppressLint
import android.bluetooth.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.girou.androiderestaurant.R
import fr.isen.girou.androiderestaurant.databinding.ActivityDeviceDetailBinding
import java.util.*

@SuppressLint("MissingPermission")
class DeviceDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeviceDetailBinding
    private var bluetoothGatt: BluetoothGatt? = null
    private lateinit var bleServiceAdapter: BLEServiceAdapter

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
                servicesDiscovered(gatt)

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

    private fun servicesDiscovered(gatt: BluetoothGatt?) {
        gatt?.services?.let {
            runOnUiThread {
                bleServiceAdapter = BLEServiceAdapter(
                    this,
                    it.map { service ->
                        Log.i("service",service.toString())
                        BLEService(service.uuid.toString(), service.characteristics)
                    }.toMutableList(),
                    { characteristic -> gatt.readCharacteristic(characteristic) },
                    { characteristic -> writeIntoCharacteristic(gatt, characteristic) },

                    )
                binding.serviceList.layoutManager = LinearLayoutManager(this@DeviceDetailActivity)
                binding.serviceList.adapter = bleServiceAdapter
                binding.serviceList.addItemDecoration(
                    DividerItemDecoration(
                        this@DeviceDetailActivity,
                        LinearLayoutManager.VERTICAL
                    )
                )
            }
        }
    }

    private fun writeIntoCharacteristic( gatt: BluetoothGatt, characteristic: BluetoothGattCharacteristic ) {
        runOnUiThread {
            val input = EditText(this)
            input.inputType = InputType.TYPE_CLASS_TEXT
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(16, 0, 16, 0)
            input.layoutParams = params

            AlertDialog.Builder(this@DeviceDetailActivity)
                .setTitle(R.string.ble_device_write_characteristic_title)
                .setView(input)
                .setPositiveButton(R.string.ble_device_write_characteristic_confirm) { _, _ ->
                    characteristic.value = input.text.toString().toByteArray()
                    gatt.writeCharacteristic(characteristic)
                    gatt.readCharacteristic(characteristic)
                }
                .setNegativeButton(R.string.ble_device_write_characteristic_cancel) { dialog, _ -> dialog.cancel() }
                .create()
                .show()
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