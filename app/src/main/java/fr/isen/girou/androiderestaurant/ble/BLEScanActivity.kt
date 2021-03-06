package fr.isen.girou.androiderestaurant.ble

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.girou.androiderestaurant.R
import fr.isen.girou.androiderestaurant.databinding.ActivityBlescanBinding

@Suppress("DEPRECATION")
class BLEScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBlescanBinding
    private val ENABLE_BLUETOOTH_REQUEST_CODE = 1
    private val ALL_PERMISSION_REQUEST_CODE = 100
    private var scanning = false
    private var adapter: BLEScanAdapter? = null

    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter

    }

    private fun startLeScanBLEWithPermission(enable: Boolean){
        if (checkAllPermissionGranted()) {
            startLeScanBLE(enable)
        }else{
            ActivityCompat.requestPermissions(this, getAllPermissions() ,ALL_PERMISSION_REQUEST_CODE)
        }
    }

    private fun checkAllPermissionGranted(): Boolean {
        return getAllPermissions().all { permission ->
            ActivityCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun getAllPermissions(): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.BLUETOOTH_SCAN,
                android.Manifest.permission.BLUETOOTH_CONNECT
            )
        } else {
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBlescanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        when {
            bluetoothAdapter?.isEnabled == true ->
                startLeScanBLEWithPermission(true)
            bluetoothAdapter != null ->
                askBluetoothPermission()
            else -> {
                displayBLEUnAvailable()
            }
        }
        binding.scanBLEBtn.setOnClickListener {
            startLeScanBLEWithPermission(!scanning)
        }
        binding.scanBLEText.setOnClickListener {
            startLeScanBLEWithPermission(!scanning)
        }
        adapter= BLEScanAdapter(arrayListOf()) {
            val intent = Intent ( this, DeviceDetailActivity::class.java)
            intent.putExtra(DEVICE_KEY, it)
            startActivity(intent)
        }
        binding.scanBLEList.layoutManager = LinearLayoutManager(this)
        binding.scanBLEList.adapter = adapter
    }

    override fun onStop(){
        super.onStop()
        startLeScanBLEWithPermission(false)
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
        @SuppressLint("NotifyDataSetChanged")
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            Log.d("BLEScanActivity", "result: ${result.device.address}, rssi : ${result.rssi}")
            adapter?.apply {
                addResultToBleList(result)
                notifyDataSetChanged()
            }
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

    private fun scanToggle() {
        if (!scanning) {
            binding.scanBLEText.text = "Lancer le scan"
            binding.scanBLEBtn.setImageResource(R.drawable.ic_baseline_play_arrow_50)
            binding.scanBLEProgressBar.visibility = View.INVISIBLE
        } else {
            binding.scanBLEText.text = "Scan en cours..."
            binding.scanBLEBtn.setImageResource(R.drawable.ic_baseline_pause_50)
            binding.scanBLEProgressBar.visibility = View.VISIBLE
        }
    }

    companion object {
        const val DEVICE_KEY = "device"
    }
}