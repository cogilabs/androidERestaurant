package fr.isen.girou.androiderestaurant

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.girou.androiderestaurant.databinding.ActivityBlescanBinding

class BLEScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBlescanBinding
    private val ENABLE_BLUETOOTH_REQUEST_CODE = 1
    private val ALL_PERMISSION_REQUEST_CODE = 100
    private var scanning = false

    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBlescanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        when {
            bluetoothAdapter?.isEnabled == true ->
                startLeScanWithPermission(true)
            bluetoothAdapter != null ->
                askBluetoothPermission()
            else -> {
                displayBLEUnAvailable()
            }
        }
        binding.scanBLEBtn.setOnClickListener {
            val test = scanning

            startLeScanWithPermission(!scanning)
        }
        binding.scanBLEText.setOnClickListener {
            startLeScanWithPermission(!scanning)
        }
    }

    override fun onStop(){
        super.onStop()
        startLeScanWithPermission(false)
    }

    private fun startLeScanWithPermission(enable: Boolean){
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ){
            startLeScanBLE(enable)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION
                /*Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN*/
            ), ALL_PERMISSION_REQUEST_CODE)
        }

    }

    @SuppressLint("MissingPermission")
    private fun startLeScanBLE(enable: Boolean) {

        bluetoothAdapter?.bluetoothLeScanner?.apply {
            if(enable) {
                scanning = true
                startScan(scanCallback)
            } else {
                scanning = false
                stopScan(scanCallback)
            }
            scanToggle()
        }
    }

    private val scanCallback = object: ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            Log.d("BLEScanActivity", "result: ${result?.device?.address}, rssi : ${result?.rssi}")
        }
    }

    private fun displayBLEUnAvailable() {
        binding.scanBLEBtn.isVisible = false
        binding.scanBLEText.text = getString(R.string.ble_scan_device_error)
        binding.scanBLEProgressBar.isVisible = false
    }

    private fun askBluetoothPermission(){
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        if(ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startActivityForResult(enableBtIntent, ENABLE_BLUETOOTH_REQUEST_CODE)
        }
    }


    private fun initScanPage() {
        binding = ActivityBlescanBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.scanBLEText.text = "Lancer le scan"
        binding.scanBLEBtn.setImageResource(R.drawable.ic_baseline_play_arrow_50)
        binding.scanBLEProgressBar.visibility = View.INVISIBLE
        binding.scanBLEProgressBar.isIndeterminate = true

        scanToggle()
    }

    private fun scanToggle() {
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

    /*override fun onResume() {
        super.onResume()
        if(!bluetoothAdapter.isEnabled) {
            promptEnableBluetooth()
        }
    }

    private fun promptEnableBluetooth() {
        if(!bluetoothAdapter.isEnabled){
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        }
    }*/
}