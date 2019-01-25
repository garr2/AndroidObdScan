package com.innowise_group.data.bt

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import com.innowise_group.domain.entity.BtState
import io.reactivex.Completable
import io.reactivex.Observable
import java.io.IOException
import java.util.*


class ConnectionBtAdapter {

    protected val bluetooth: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    var socket: BluetoothSocket? = null

    fun getState(): Observable<BtState> {

        val state = BtState(
            true,
            bluetooth.isEnabled ?: false,
            bluetooth.state ?: -1
        )

        return Observable.just(state)
    }

    fun getDeviceList(): Observable<Set<BluetoothDevice>> {
        return Observable.just(bluetooth.bondedDevices)
    }

    fun connect(deviceAddress: String): Completable {
        return try {
            val device = bluetooth.getRemoteDevice(deviceAddress)
            val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
            socket = device.createInsecureRfcommSocketToServiceRecord(uuid)
            socket?.connect()

            if (socket?.isConnected == true) {
                Completable.complete()
            } else Completable.error(IOException())
        } catch (e: IOException) {
            Completable.error(e)
        }
    }

    fun cancel() {

        socket?.close()
    }


}