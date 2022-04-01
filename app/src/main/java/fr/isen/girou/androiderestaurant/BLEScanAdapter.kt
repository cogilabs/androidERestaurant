package fr.isen.girou.androiderestaurant

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanResult
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

internal class BLEScanAdapter(val bleList: ArrayList<ScanResult>, val clickListener: (BluetoothDevice) -> Unit) :
    RecyclerView.Adapter<BLEScanAdapter.BLEViewHolder>() {

        internal inner class BLEViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var puissanceTextView: TextView = view.findViewById(R.id.puissanceTextView)
            var deviceTextView: TextView = view.findViewById(R.id.deviceTextView)
            var macTextView: TextView = view.findViewById(R.id.macTextView)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BLEViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.device, parent, false)
        return BLEViewHolder(itemView)
    }

    @SuppressLint("MissingPermission")
    override fun onBindViewHolder(holder: BLEViewHolder, position: Int) {
        val result = bleList[position]
        holder.puissanceTextView.text = result.rssi.toString()
        holder.deviceTextView.text = result.device.name
        holder.macTextView.text = result.device.address
        holder.itemView.setOnClickListener{
            clickListener(result.device)
        }
    }

    fun addResultToBleList(scanResult: ScanResult){
        val indexOfResult = bleList.indexOfFirst {
            it.device.address == scanResult.device.address
        }
        if(indexOfResult != -1) {
            bleList[indexOfResult] = scanResult
            //  notifyItemChanged(indexOfResult)
        } else {
            bleList.add(scanResult)
            //  notifyItemInserted(bleList.size -1)
        }
    }

    override fun getItemCount(): Int {
        return bleList.size
    }
}