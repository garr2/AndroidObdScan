package com.innowise_group.data.bt

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import com.innowise_group.domain.entity.BtState
import io.reactivex.Completable
import io.reactivex.Observable
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*


class BaseBtAdapter {

    protected val bluetooth: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    protected val socked: BluetoothSocket? = null

    private var btInputStream: InputStream? = null
    private var btOutputStream: OutputStream? = null

    private fun initStreams() {
        try {
            btInputStream = socked?.inputStream
            btOutputStream = socked?.outputStream
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getState(): Observable<BtState> {

        val state = BtState(
            bluetooth != null,
            bluetooth?.isEnabled ?: false,
            bluetooth?.state ?: -1
        )

        return Observable.just(state)
    }

    fun getDeviceList(): Observable<Set<BluetoothDevice>> {
        return Observable.just(bluetooth?.bondedDevices)
    }

    fun connect(deviceAddress: String): Completable {
        return try {
            val device = bluetooth?.getRemoteDevice(deviceAddress)
            val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
            val socket = device?.createInsecureRfcommSocketToServiceRecord(uuid)
            socket?.connect()
            Completable.complete()
        } catch (e: IOException) {
            Completable.error(e)
        }
    }

    fun write(bytes: ByteArray): Completable {
        return try {
            btOutputStream?.write(bytes)
            Completable.complete()
        } catch (e: IOException) {
            Completable.error(e)
        }
    }

    fun read(): Observable<ByteArray> {
        var buffer = ByteArray(1024)
        var bytes = 0

        while (bytes >= 0)
            try {
                bytes = btInputStream!!.read(buffer)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        return Observable.just(buffer)
    }

    fun cancel() {
        socked?.close()
    }


}