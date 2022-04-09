package fr.isen.girou.androiderestaurant.ble

import android.bluetooth.BluetoothProfile
import androidx.annotation.StringRes
import fr.isen.girou.androiderestaurant.R

enum class BLEConnexionState (val state: Int,@StringRes val text: Int) {
    STATE_CONNECTING(BluetoothProfile.STATE_CONNECTING, R.string.ble_device_connecting),
    STATE_CONNECTED(BluetoothProfile.STATE_CONNECTED, R.string.ble_device_connected),
    STATE_DISCONNECTED(BluetoothProfile.STATE_DISCONNECTED, R.string.ble_device_disconnected),
    STATE_DISCONNECTING(BluetoothProfile.STATE_DISCONNECTING, R.string.ble_device_disconnecting);

    companion object {
        fun getBLEConnexionStateFromState(state: Int) = values().firstOrNull {
            it.state == state
        }
    }
}